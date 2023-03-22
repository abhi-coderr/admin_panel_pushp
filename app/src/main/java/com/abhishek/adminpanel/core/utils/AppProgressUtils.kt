package com.oneclick.blackandoneparent.core.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.oneclick.blackandoneparent.R

/**
 * Description: This class is used for the purpose of creating and showing progress.
 *
 * @author Ankit Mishra
 * @since 01/12/21
 *
 * @param activity Provide activity.
 */
class AppProgressUtils(private val activity: Activity) {

    /**
     * This is used for showing and hiding progress.
     */
    private var dialogProgress: Dialog? = null


    /**
     * Description: This method is used for the purpose of showing and hiding progress bar.
     *
     * @author Ankit Mishra
     * @since 02/11/21
     *
     * @param showProgress Provide true to show the progress and false to hide it.
     */
    fun showProgressOrHideIt(showProgress: Boolean) {
        if (showProgress) {
            if (!activity.isFinishing && !activity.isDestroyed && (dialogProgress == null || !dialogProgress!!.isShowing)) {
                /**
                 * Initializing the dialog.
                 */
                dialogProgress = Dialog(activity)
                /**
                 * Setting up the dialog.
                 */
                dialogProgress?.apply {
                    /**
                     * Requesting widow.
                     */
                    requestWindowFeature(Window.FEATURE_NO_TITLE)
                    /**
                     * Making progress not cancelable.
                     */
                    setCancelable(false)
                    /**
                     * Setting up the dialog layout.
                     */
                    setContentView(R.layout.raw_progress_layout)
                    /**
                     * Setting up the transparent background.
                     */
                    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    /**
                     * Setting up the lottie animation and showing it.
                     */
                    val mLottieAnimationView: LottieAnimationView = findViewById(
                        R.id.raw_progress_layout_animationview
                    )
                    mLottieAnimationView.apply {
                        /**
                         * Getting image from the assets folder.
                         */
                        imageAssetsFolder = "images/"
                        /**
                         * Setting up the animation type.
                         */
                        setAnimation("animation_loader.json")
                        /**
                         * Setting up the repeat count.
                         */
                        repeatCount = LottieDrawable.INFINITE
                        /**
                         * Playing the progress animation.
                         */
                        playAnimation()
                    }
                    /**
                     * Showing the dialog.
                     */
                    if (!isShowing) show()
                }
            }
        } else {
            /**
             * Hiding the progress.
             */
            dialogProgress?.apply {
                if (isShowing) {
                    dismiss()
                }
            }

            /**
             * Resetting the dialog.
             */
            dialogProgress = null
        }
    }
}