package com.oneclick.blackandoneparent.core.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.oneclick.blackandoneparent.R


/**
 * Description: This class is used for the purpose of creating and showing dialogs.
 *
 * @author Ankit Mishra
 * @since 25/10/21
 *
 * @param activity Provide activity.
 */
class AppDialogUtils(private val activity: Activity) {

    /**
     * Description: This method is used for the purpose of generating new dialog with supplied parameters.
     *
     * @author Ankit Mishra
     * @since 11/10/21
     *
     * @param messageText Provide alert dialog message
     * @param positiveText Provide text which should be of positive button
     * @param positiveButtonClick Provide function which should be executed on tapping positive button
     * @param negativeText Provide text which should be of negative button. Don't pass anything if negative button is not required.
     * @param negativeButtonClick Provide function which should be executed on tapping negative button
     * @param titleText Provide alert dialog title. By Default, it is app name.
     * @param isCancelable Provide true if it should be cancelable else false. By default, it is true.
     *
     * @return Alert Dialog with supplied parameters.
     */
    fun getNewDialog(
        messageText: String,
        positiveText: String,
        positiveButtonClick: (dialogInterface: DialogInterface) -> Unit,
        negativeText: String? = null,
        negativeButtonClick: ((dialogInterface: DialogInterface) -> Unit)? = null,
        titleText: String = activity.getString(R.string.app_name),
        isCancelable: Boolean = true
    ): AlertDialog =
        AlertDialog.Builder(activity).apply {
            setTitle(titleText)
            setCancelable(isCancelable)
            setMessage(messageText)
            setPositiveButton(positiveText) { dialog, _ ->
                positiveButtonClick(dialog)
            }
            negativeText?.let {
                setNegativeButton(negativeText) { dialog, _ ->
                    negativeButtonClick?.invoke(dialog)
                }
            }
        }.create()

    /**
     * Description: This method is used for showing exit dialog.
     *
     * @author Ankit Mishra
     * @since 01/02/22
     *
     * @param messageText Provide alert dialog message
     * @param positiveText Provide text which should be of positive button
     * @param positiveButtonClick Provide function which should be executed on tapping positive button
     * @param negativeText Provide text which should be of negative button. Don't pass anything if negative button is not required.
     * @param negativeButtonClick Provide function which should be executed on tapping negative button
     */
    fun getExitDialog(
        messageText: String = activity.getString(R.string.exit_dialog_title),
        positiveText: String = activity.getString(R.string.text_title_yes),
        positiveButtonClick: (dialogInterface: DialogInterface) -> Unit = { dialogInterface ->
            activity.finishAffinity()
            dialogInterface.dismiss()
        },
        negativeText: String = activity.getString(R.string.text_title_no),
        negativeButtonClick: ((dialogInterface: DialogInterface) -> Unit) = { dialogInterface ->
            dialogInterface.dismiss()
        },
    ) = getNewDialog(
        messageText,
        positiveText,
        positiveButtonClick,
        negativeText,
        negativeButtonClick,
        isCancelable = false
    )

    /**
     * Description: This method is used for showing unauthorized dialog.
     *
     * @author Ankit Mishra
     * @since 02/02/22
     *
     * @param messageText Provide alert dialog message
     * @param positiveText Provide text which should be of positive button
     * @param positiveButtonClick Provide function which should be executed on tapping positive button
     */
    fun getUnAuthDialog(
        messageText: String = activity.getString(R.string.unauth_dialog_title),
        positiveText: String = activity.getString(R.string.text_title_yes),
        positiveButtonClick: (dialogInterface: DialogInterface) -> Unit
    ) = getNewDialog(
        messageText,
        positiveText,
        positiveButtonClick,
        isCancelable = false
    )

    /**
     * Description: This method is used for getting custom dialog.
     *
     * @author Ankit Mishra
     * @since 04/02/22
     */
    fun <T : ViewDataBinding> getCustomDialog(
        @LayoutRes layoutResource: Int,
        binding: (T) -> View
    ): AlertDialog {
        /**
         * Creating Dialog.
         */
        val dialog = AlertDialog.Builder(activity).setView(
            DataBindingUtil.inflate<T>(
                activity.layoutInflater,
                layoutResource,
                null,
                false
            ).apply {
                //Setting up the success messages
                binding(this)
            }.root
        ).create()

        /**
         * Configuring Dialog.
         */
        return dialog.apply {
            /**
             * Applying window setting.
             */
            window?.apply {
                /**
                 * Setting background transparent.
                 */
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                /**
                 * Setting animations.
                 */
                attributes.windowAnimations = R.style.AppDialogTheme
                /**
                 * Setting dialog gravity.
                 */
                attributes.gravity = Gravity.CENTER
            }
            /**
             * Stopping user from canceling it.
             */
            setCancelable(false)
        }
    }

    /**
     * Description: This method is used for getting custom bottom sheet dialog.
     *
     * @author Ankit Mishra
     * @since 07/02/22
     *
     * @param layoutResource Provide layout resource id.
     * @param isCancelable Provide false if the dialog should not be cancelable. By default, this is true.
     * @param binding Set up the binding.
     */
    fun <T : ViewDataBinding> getCustomBottomSheetDialog(
        @LayoutRes layoutResource: Int,
        isCancelable: Boolean = true,
        binding: (T, DialogInterface) -> View
    ): Pair<BottomSheetDialog, T> {
        var bindingToBeReturned: T

        /**
         * Creating Dialog.
         */
        val dialog =
            BottomSheetDialog(activity, R.style.FullWidth_Dialog).let { bottomSheetDialog ->

                bottomSheetDialog.apply {
                    bindingToBeReturned = DataBindingUtil.inflate<T>(
                        activity.layoutInflater,
                        layoutResource,
                        null,
                        false
                    ).apply {
                        //Setting up the success messages
                        binding(this, bottomSheetDialog)
                    }
                    setContentView(
                        bindingToBeReturned.root
                    )

                    setCancelable(isCancelable)
                    setCanceledOnTouchOutside(isCancelable)
                    behavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        return Pair(dialog, bindingToBeReturned)
    }
}