package com.oneclick.blackandoneparent.core.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toolbar
import androidx.annotation.ColorRes
import androidx.core.view.isVisible
import com.oneclick.blackandoneparent.core.utils.setSafeOnClickListener
import com.oneclick.blackandoneparent.R
import com.oneclick.blackandoneparent.databinding.BaseToolbarBinding

/**
 * Description: This is Custom Toolbar which reduces the redundant code from the fragments.
 *
 * @author Ankit Mishra
 * @since 31/01/22
 */
class CustomToolbar @JvmOverloads
constructor(
    private val ctx: Context,
    private val attributeSet: AttributeSet? = null,
    private val defStyleAttr: Int = 0
) : Toolbar(ctx, attributeSet, defStyleAttr) {

    /**
     * Private Binding Variable.
     */
    private var _binding: BaseToolbarBinding? = null

    /**
     * Binding Variable.
     */
    private val binding get() = _binding!!

    init {
        /**
         * Creating inflater from the context
         */
        val inflater = LayoutInflater.from(ctx)

        /**
         * Inflating the layout and creating data binding for setting up the layout.
         */
        _binding = BaseToolbarBinding.inflate(inflater, this, true)

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        /**
         * Freeing up the binding variable.
         */
        _binding = null
    }

    /**
     * Description: This data class is used for configuring toolbar properties.
     *
     * @author Ankit Mishra
     * @since 01/02/22
     */
    data class ToolbarConfiguration(
        val title: String = String(),
        val isTitleVisible: Boolean = true,//generally title is visible
        val isBackButtonVisible: Boolean = true,//generally back button is visible
        val isDrawerVisible: Boolean = false,
        val onBackButtonClick: (() -> Unit)? = null,
        val onDrawerButtonClick: (() -> Unit)? = null,
        @ColorRes val backgroundColor: Int = R.color.white
    )

    /**
     * Description: This method is used for setting up the toolbar configuration.
     *
     * @author Ankit Mishra
     * @since 01/02/22
     *
     * @param toolbarConfiguration Provide toolbar configuration object.
     */
    fun setToolbar(toolbarConfiguration: ToolbarConfiguration) {

        binding.apply {

            toolbarConfiguration.let { config ->
                /**
                 * Setting up the text views.
                 */
                toolbarTitleTv.text = config.title

                /**
                 * Setting up the visibilities.
                 */
                toolbarBackBtn.isVisible = config.isBackButtonVisible


                /**
                 * Setting up the background.
                 */
                toolbarMainCl.setBackgroundResource(config.backgroundColor)

                /**
                 * Setting up the on click listeners.
                 */
                toolbarBackBtn.setSafeOnClickListener {
                    config.onBackButtonClick?.invoke()
                }
            }
        }

    }
}