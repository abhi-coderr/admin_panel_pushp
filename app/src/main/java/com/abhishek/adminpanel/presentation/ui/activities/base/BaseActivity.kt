package com.oneclick.blackandoneparent.presentation.ui.activities.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.oneclick.blackandoneparent.core.utils.AppProgressUtils
import com.oneclick.blackandoneparent.core.utils.ScreenTransitionUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var appProgressUtils: AppProgressUtils

    private var screenTransitionUtils: ScreenTransitionUtils? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screenTransitionUtils = ScreenTransitionUtils(this).also {
            it.overrideAnimationStart()
        }
//
//        onBackPressedDispatcher.addCallback(this) {
//            screenTransitionUtils?.overrideAnimationEnd()
//            finish()
//        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        screenTransitionUtils?.overrideAnimationEnd()
    }

    /**
     * Description: This method is used for the purpose of showing and hiding progress bar.
     *
     * @author Ankit Mishra
     * @since 13/03/23
     *
     * @param showProgress Provide true to show the progress and false to hide it.
     */
    fun showProgressOrHideIt(showProgress: Boolean) {
        appProgressUtils.showProgressOrHideIt(showProgress)
    }


}