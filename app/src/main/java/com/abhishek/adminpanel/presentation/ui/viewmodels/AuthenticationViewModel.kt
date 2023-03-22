package com.oneclick.blackandoneparent.presentation.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oneclick.androidcoe.core.network_helpers.Resource
import com.oneclick.blackandoneparent.core.constants.AppConstants
import com.oneclick.blackandoneparent.core.utils.AppPreference
import com.oneclick.blackandoneparent.core.utils.Event
import com.oneclick.blackandoneparent.core.utils.showHideLoader
import com.oneclick.blackandoneparent.data.model.ResendOtpResponse
import com.oneclick.blackandoneparent.data.model.VerifyOtpResponse
import com.oneclick.blackandoneparent.data.model.VerifyPhoneNumberResponse
import com.oneclick.blackandoneparent.domain.usecases.authentication.LoginUseCase
import com.oneclick.blackandoneparent.domain.usecases.authentication.LogoutUseCase
import com.oneclick.blackandoneparent.domain.usecases.authentication.ResendOtpUseCase
import com.oneclick.blackandoneparent.domain.usecases.authentication.VerifyOtpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Description: This view model is used for the purpose of handling authentication tasks.
 *
 * @author Ankit Mishra
 * @since 01/12/21
 *
 */
@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val verifyOtpUseCase: VerifyOtpUseCase,
    private val resendOtpUseCase: ResendOtpUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val appPreference: AppPreference
) : ViewModel() {

    //Login Data
    var strLoginUsername = MutableLiveData(String())
    var strLoginPassword = MutableLiveData(String())
    var strLoginPhoneNumber = MutableLiveData(String())
    var booleanIsRememberMe = MutableLiveData(true)

    //Private variables
    private var _showMessage = MutableLiveData<Event<String>>()
    private var _loginApiResponse = MutableLiveData<Event<Resource<VerifyPhoneNumberResponse>>>()
    private var _verifyOtpApiResponse = MutableLiveData<Event<Resource<VerifyOtpResponse>>>()
    private var _resendOtpApiResponse =
        MutableLiveData<Event<Resource<VerifyPhoneNumberResponse>>>()

    //Live data variables
    val showMessage: LiveData<Event<String>> = _showMessage
    val loginApiResponse: LiveData<Event<Resource<VerifyPhoneNumberResponse>>> = _loginApiResponse
    val verifyOtpApiResponse: LiveData<Event<Resource<VerifyOtpResponse>>> = _verifyOtpApiResponse
    val resendOtpApiResponse: LiveData<Event<Resource<VerifyPhoneNumberResponse>>> =
        _resendOtpApiResponse

    /**
     * Description: This method is used for the purpose of calling the login api.
     *
     * @author Ankit Mishra
     * @since 13/03/23
     */
    fun doLoginByOtp() {
        viewModelScope.launch {
            showHideLoader(_loginApiResponse) {
                //Calling Api and setting up the data in variable.
                val response = loginUseCase.execute(
                    phoneNumber = strLoginPhoneNumber.value.orEmpty()
                )
                _loginApiResponse.value = Event(response)

            }
        }
    }


    /**
     * Description: This method is used for the purpose of calling the verify otp api.
     *
     * @author Ankit Mishra
     * @since 13/03/23
     *
     * @param phoneNumber Provide the phone number.
     * @param otp Provide the otp
     */
    fun verifyOtp(phoneNumber: String, otp: String) {
        viewModelScope.launch {
            showHideLoader(_verifyOtpApiResponse) {
                //Calling Api and setting up the data in variable.
                val response = verifyOtpUseCase.execute(
                    phoneNumber = phoneNumber,
                    otp = otp
                )
                //Saving the access token if the request is of otp type LOGIN
                if (response is Resource.Success) {
                    appPreference.writeString(
                        AppConstants.PREF_ACCESS_TOKEN,
                        response.data.token.toString()
                    )
                }
            }
        }
    }

    /**
     * Description: This method is used for the purpose of calling the login api.
     *
     * @author Ankit Mishra
     * @since 13/03/23
     *
     * @param phoneNumber Provide the phoneNumber.
     */
    fun resendOtp(phoneNumber: String) {
        viewModelScope.launch {
            showHideLoader(_resendOtpApiResponse) {
                //Calling Api and setting up the data in variable.
                val response = resendOtpUseCase.execute(
                    phoneNumber = phoneNumber
                )
                _resendOtpApiResponse.value = Event(response)
            }
        }
    }


}