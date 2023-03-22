package com.oneclick.blackandoneparent.core.custom_views

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oneclick.blackandoneparent.core.utils.ifThrowsPrintStackTrace

/**
 * Description: This class is used for the purpose of handling checkable items in Custom Adapter.
 *
 * @author Ankit Mishra
 * @since 29/04/22
 *
 * @param isChecked This variable is used for handling checking scenarios.
 */
open class CheckableDataClass(
    var isChecked: Boolean = false
)

/**
 * Description: This interface is used for handling check list actions in Custom Adapter.
 *
 * @author Ankit Mishra
 * @since 29/04/22
 */
interface CheckListActions<T> {
    /**
     * Description: This method is used for checking the provided item like Radio Button.
     * It means that only selected item will be checked and other items will be unchecked automatically.
     *
     * @author Ankit Mishra
     * @since 29/01/22
     *
     * @param whichItemShouldBeChecked Provide function which should return true if the item should be checked else false.
     */
    fun checkLikeRadioButton(whichItemShouldBeChecked: (T) -> Boolean)

    /**
     * Description: This method is used for checking the provided item like checklist.
     * It means that selected items will be checked and previously checked items will remain checked.
     *
     * @author Ankit Mishra
     * @since 29/01/22
     *
     * @param whichItemShouldBeChecked Provide function which should return true if the item should be checked else false.
     */
    fun checkLikeCheckList(whichItemShouldBeChecked: (T) -> Boolean)

    /**
     * Description: This method is used for getting selected item. Use this when the list is of radio button type.
     *
     * @author Ankit Mishra
     * @since 29/04/22
     */
    fun getSelectedItem(): T?


    /**
     * Description: This method is used for getting selected item. Use this when the list is of check list type.
     *
     * @author Ankit Mishra
     * @since 29/04/22
     */
    fun getSelectedItems(): List<T>
}

/**
 * Description: This custom adapter class is used for the purpose of reducing the redundant code in recyclerview adapters.
 *
 * @author Ankit Mishra
 * @since 29/04/22
 *
 * @param layoutResource Provide layout resource of your raw item.
 * @param onBind Provide on Bind method. This will be used for setting up the row binding.
 * @param isSameItems Provide the function to match ids of both items. (Old and the new one.)
 * @param isSameItemContent Provide the function to match content of both items. (Old and the new one.)
 * @param deepCopy Provide a function which can deep copy the item. This is important for reducing reference issues.
 */
class CustomAdapter<T : Any, B : androidx.databinding.ViewDataBinding>(
    @LayoutRes private val layoutResource: Int,
    private val onBind: (binding: B, item: T, checklistActions: CheckListActions<T>) -> Unit,
    private val isSameItems: (oldItem: T, newItem: T) -> Boolean,
    private val isSameItemContent: (oldItem: T, newItem: T) -> Boolean,
    private val deepCopy: (T) -> T
) :
    ListAdapter<T, CustomAdapter<T, B>.CustomViewHolder<B>>(
        object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T) = isSameItems(oldItem, newItem)

            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
                val isSameClassContent = isSameItemContent(oldItem, newItem)
                var isCheckableMatched = true

                if (oldItem is CheckableDataClass) {
                    //Checking for the checkable method.
                    if (newItem is CheckableDataClass) {
                        isCheckableMatched = oldItem.isChecked == newItem.isChecked
                    }
                }
                return isSameClassContent && isCheckableMatched
            }

            override fun getChangePayload(oldItem: T, newItem: T): Any? {
                if (oldItem != newItem) {
                    return newItem
                }
                return super.getChangePayload(oldItem, newItem)
            }
        }
    ), CheckListActions<T> {

    /**
     * Description: This view holder class is used for managing views in Custom Adapter.
     *
     * @author Ankit Mishra
     * @since 29/04/22
     *
     * @param binding Provide view binding for the adapter view.
     */
    inner class CustomViewHolder<B : androidx.databinding.ViewDataBinding>(
        val binding: B
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder<B> {
        val binding = DataBindingUtil.inflate<B>(
            LayoutInflater.from(parent.context),
            layoutResource,
            parent,
            false
        )
        return CustomViewHolder<B>(binding)
    }


    override fun onBindViewHolder(holder: CustomViewHolder<B>, position: Int) {
        onBind(holder.binding, getItem(position), this)
    }

    override fun onBindViewHolder(
        holder: CustomViewHolder<B>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            ifThrowsPrintStackTrace {
                val newItem = payloads[0] as T?
                newItem?.let {
                    onBind(holder.binding, newItem, this)
                }
            }
        }
    }

    override fun checkLikeRadioButton(whichItemShouldBeChecked: (T) -> Boolean) {
        val currentList = currentList.toMutableList().map { item -> deepCopy(item) }
        for (singleItem in currentList) {
            if (singleItem is CheckableDataClass) {
                singleItem.isChecked = whichItemShouldBeChecked(singleItem)
            } else {
                Log.d(
                    "CustomAdapter",
                    "checkLikeRadioButton()-> The provided class is not extending CheckableDataClass"
                )
            }
        }

        submitList(currentList.toMutableList())
    }


    override fun checkLikeCheckList(whichItemShouldBeChecked: (T) -> Boolean) {
        val currentList = currentList.toMutableList().map { item -> deepCopy(item) }

        for (singleItem in currentList) {
            if (singleItem is CheckableDataClass) {
                if (whichItemShouldBeChecked(singleItem)) {
                    singleItem.isChecked = true
                }
            } else {
                Log.d(
                    "CustomAdapter",
                    "checkLikeRadioButton()-> The provided class is not extending CheckableDataClass"
                )
            }
        }

        submitList(currentList.toMutableList())
    }

    override fun getSelectedItem(): T? {
        return currentList.find { item ->
            if (item is CheckableDataClass) {
                item.isChecked
            }else {
                false
            }
        }
    }

    override fun getSelectedItems(): List<T> {
        val currentList = currentList.toMutableList().map { item -> deepCopy(item) }
        return currentList.filter { item ->
            if (item is CheckableDataClass) {
                item.isChecked
            }
            false
        }

    }
}