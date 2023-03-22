package com.oneclick.blackandoneparent.presentation.ui.activities.authentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.oneclick.androidcoe.core.network_helpers.Resource
import com.oneclick.blackandoneparent.R
import com.oneclick.blackandoneparent.core.constants.AppConstants.INTENT_KEY_VERIFY_OTP_SCREEN
import com.oneclick.blackandoneparent.core.custom_views.CustomEditTextView
import com.oneclick.blackandoneparent.core.utils.*
import com.oneclick.blackandoneparent.data.model.VerifyPhoneNumberResponse
import com.oneclick.blackandoneparent.data.model.intent.VerifyOtpIM
import com.oneclick.blackandoneparent.databinding.ActivityLoginBinding
import com.oneclick.blackandoneparent.presentation.ui.activities.base.BaseActivity
import com.oneclick.blackandoneparent.presentation.ui.viewmodels.AuthenticationViewModel

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val authenticationViewModel: AuthenticationViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        setUpBindings()

        setUpViewModel()

        splashAndLoginAnimation()

    }

    private var isSplashAnimationNotPerformed = true

    /**
     * Description: This function is provide animation
     *              which is done at login screen.
     *
     * @author Abhishek Oza
     * @since 16/03/23
     */
    private fun splashAndLoginAnimation() = binding.apply {
        if (isSplashAnimationNotPerformed) {
            isSplashAnimationNotPerformed = false
            executeAfterSomeTime(1000) {

                val animationSpread =
                    AnimationUtils.loadAnimation(this@LoginActivity, R.anim.explor_water_animation)
                        .apply {
                            duration = 3100
                            interpolator = AccelerateDecelerateInterpolator()
                        }

                val animationUp =
                    AnimationUtils.loadAnimation(this@LoginActivity, R.anim.slide_up).apply {
                        duration = 1200
                        interpolator = AccelerateDecelerateInterpolator()
                    }

                val slideUp =
                    AnimationUtils.loadAnimation(this@LoginActivity, R.anim.slide_up_sheet)

                mainLogo.startAnim(animationUp)

                spreadView.isVisible = true

                spreadView.startAnim(animation = animationSpread, onEnd = {
                    splaceCl.setBackgroundColor(
                        ContextCompat.getColor(
                            this@LoginActivity,
                            R.color.app_theme_color
                        )
                    )
                    spreadView.isVisible = false
                })

                executeAfterSomeTime(300) {
                    bottomLayoutCl.isVisible = true
                    bottomLayoutCl.startAnim(slideUp)
                }

            }
        }
    }

    /**
     * Description: This method is used for the purpose of setting up the fragment view bindings.
     *
     * @author Ankit Mishra
     * @since 13/03/23
     */
    private fun setUpBindings() = binding.apply {

        authenticationViewModel = this@LoginActivity.authenticationViewModel

        mobileNumberEt.setOnFocusChangeListener { _, focus ->
            if (focus) {
                appLogo.visibility = View.VISIBLE
                mainLogo.clearAnimation()
                mainLogo.visibility = View.GONE
                val scale = resources.displayMetrics.density
                val dpAsPixels = (380 * scale + 0.5f)
                bottomLayoutCl.setPadding(0, 0, 0, dpAsPixels.toInt())
            } else {
                appLogo.visibility = View.GONE
                mainLogo.visibility = View.VISIBLE
            }
        }

        loginBtn.setSafeOnClickListener {
            if (CustomEditTextView.areAllTheseEditTextsValid(
                    arrayListOf(
                        mobileNumberEt
                    )
                )
            ) {
                this@LoginActivity.authenticationViewModel.doLoginByOtp()
            }
        }

    }

    /**
     * Description: This method is used for the purpose of setting up the view model observers.
     *
     * @author Ankit Mishra
     * @since 13/03/23
     */
    private fun setUpViewModel() = authenticationViewModel.apply {
        //Setting up the observer for Login Api call.
        loginApiResponse.observeNotHandledEvent(this@LoginActivity) { response ->
            //Handling the api response.
            handleLoginApiResponse(response)
        }
    }


    /**
     * Description: This method is used for the purpose of handling get login api response.
     *
     * @author Ankit Mishra
     * @since 01/12/21
     *
     * @param response Provide the response object.
     * @return Unit
     */
    private fun handleLoginApiResponse(response: Resource<VerifyPhoneNumberResponse>) {
        when (response) {
            is Resource.Loading -> showProgressOrHideIt(response.showProgress)
            is Resource.Success -> navigateToOtpScreen(response.data)
            is Resource.Failure -> showMessage(message = response.message.orEmpty())
        }
    }

    /**
     * Description: This method is used for the purpose of navigating from this fragment to Otp Screen.
     *
     * @author Ankit Mishra
     * @since 24/12/21
     */
    private fun navigateToOtpScreen(response: VerifyPhoneNumberResponse) = ifThrowsPrintStackTrace {
        //Navigating for verifying OTP
        startActivity(Intent(this, VerifyOtpActivity::class.java).apply {
            putExtra(
                INTENT_KEY_VERIFY_OTP_SCREEN, VerifyOtpIM(
                    phoneNumber = authenticationViewModel.strLoginPhoneNumber.value.orEmpty(),
                    otp = response.otp.toString()
                )
            )
        })
    }
}