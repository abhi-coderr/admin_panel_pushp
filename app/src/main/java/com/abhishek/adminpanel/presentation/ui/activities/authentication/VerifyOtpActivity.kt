package com.oneclick.blackandoneparent.presentation.ui.activities.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.oneclick.androidcoe.core.network_helpers.Resource
import com.oneclick.blackandoneparent.R
import com.oneclick.blackandoneparent.core.constants.AppConstants
import com.oneclick.blackandoneparent.core.utils.observeNotHandledEvent
import com.oneclick.blackandoneparent.core.utils.parcelable
import com.oneclick.blackandoneparent.core.utils.setSafeOnClickListener
import com.oneclick.blackandoneparent.core.utils.showMessage
import com.oneclick.blackandoneparent.data.model.intent.VerifyOtpIM
import com.oneclick.blackandoneparent.databinding.ActivityVerifyOtpBinding
import com.oneclick.blackandoneparent.presentation.ui.activities.base.BaseActivity
import com.oneclick.blackandoneparent.presentation.ui.activities.dashboard.DashboardActivity
import com.oneclick.blackandoneparent.presentation.ui.viewmodels.AuthenticationViewModel

class VerifyOtpActivity : BaseActivity() {


    //Binding Variable.
    private lateinit var binding: ActivityVerifyOtpBinding

    //View model variable.
    private val authenticationViewModel: AuthenticationViewModel by viewModels()

    private lateinit var verifyOtpIM: VerifyOtpIM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_verify_otp)

        getDataFromIntent()

        setUpBindings()

        setUpViewModel()
    }

    private fun getDataFromIntent() {
        intent?.let {
            verifyOtpIM = it.parcelable(AppConstants.INTENT_KEY_VERIFY_OTP_SCREEN)!!
            showMessage(verifyOtpIM.otp)
        }
    }

    /**
     * Description: This method is used for the purpose of setting up the fragment view bindings.
     *
     * @author Ankit Mishra
     * @since 01/12/21
     */
    private fun setUpBindings() = binding.apply {
        this@VerifyOtpActivity.authenticationViewModel.apply {

            loginBtn.setSafeOnClickListener {
                if (pinview.value.isNotEmpty() && pinview.value.length == 6) {
                    verifyOtp(
                        phoneNumber = verifyOtpIM.phoneNumber,
                        otp = pinview.value.toString()
                    )
                } else {
                    Toast.makeText(
                        this@VerifyOtpActivity,
                        "enter valid otp",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    /**
     * Description: This method is used for the purpose of setting up the view model observers.
     *
     * @author Ankit Mishra
     * @since 01/12/21
     */
    private fun setUpViewModel() = authenticationViewModel.apply {

        showMessage.observeNotHandledEvent(this@VerifyOtpActivity) { message ->
            //Showing the message.
            showMessage(message)
        }

        /**
         * Setting up the resent api response observer
         */
        resendOtpApiResponse.observeNotHandledEvent(this@VerifyOtpActivity) { response ->
            when (response) {
                is Resource.Loading -> showProgressOrHideIt(response.showProgress)
                is Resource.Success -> showMessage(message = response.data.message)
                is Resource.Failure -> showMessage(message = response.message.orEmpty())
            }
        }

        //Setting up the verify otp api response observer
        verifyOtpApiResponse.observeNotHandledEvent(this@VerifyOtpActivity) { response ->
            when (response) {
                is Resource.Loading -> showProgressOrHideIt(response.showProgress)
                is Resource.Success -> navigateToDashboardScreen()
                is Resource.Failure -> showMessage(message = response.message.orEmpty())
            }
        }

    }

    /**
     * Description: This method is used for the purpose of navigating to dashboard screen.
     *
     * @author Ankit Mishra
     * @since 07/12/21
     */
    private fun navigateToDashboardScreen() {
        startActivity(
            Intent(
                this@VerifyOtpActivity,
                DashboardActivity::class.java
            )
        )
    }

}