package com.oneclick.blackandoneparent.core.utils

import android.app.Activity
import com.oneclick.blackandoneparent.R
import com.oneclick.blackandoneparent.presentation.ui.activities.authentication.LoginActivity

class ScreenTransitionUtils(private val activity: Activity) {


    private var isActivityAnimationDone = true
    private var shouldDoTopToBottomTransitionOnScreenChange = false

    //Check If Animation Is Running When Sliding Activity
    private fun checkIsAnimationDone() {
        val thread: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(activity.resources.getInteger(android.R.integer.config_longAnimTime).toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    isActivityAnimationDone = true
                }
            }
        }
        thread.start()
    }

    //Override Animation On Going to Next Screen
    fun overrideAnimationStart() {
        if (isActivityAnimationDone) {
            isActivityAnimationDone = false
            checkIsAnimationDone()

            if (activity !is LoginActivity) {
//                if (shouldDoTopToBottomTransitionOnScreenChange) {
//                    activity.overridePendingTransition(R.anim.trans_top_in, R.anim.trans_top_out)
//                } else {
                    activity.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)
//                }
            }
        }
    }


    //Override Animation On Going to Previous Screen
    fun overrideAnimationEnd() {
        if (isActivityAnimationDone) {
            isActivityAnimationDone = false
            checkIsAnimationDone()

//            if (this !is ActivityDashboard) {
//                if (shouldDoTopToBottomTransitionOnScreenChange) {
//                    overridePendingTransition(R.anim.trans_bottom_in, R.anim.trans_bottom_out)
//                } else {
                    activity.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out)
//                }
//
//            }
        }
    }
}