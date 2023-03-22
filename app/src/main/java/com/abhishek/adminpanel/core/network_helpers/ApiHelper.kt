package com.oneclick.blackandoneparent.core.network_helpers

import android.content.Context
import com.oneclick.androidcoe.core.network_helpers.Resource
import com.oneclick.blackandoneparent.R
import com.oneclick.blackandoneparent.core.MyApplication
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Description: This object is used for the purpose of reducing the boilerplate code of calling apis.
 *
 * @author Ankit Mishra
 * @since 01/12/21
 */
class ApiHelper(private val context: Context) {


    /**
     * Description : Does General Exception Handling
     *
     * @author Ankit Mishra
     * @since 01/11/21
     *
     * @param context Provide the context to get strings from resource file.
     * @param throwable Provide throwable object.
     *
     * @return Resource with error.
     */
    private fun <T> generalExceptionHandling(throwable: Throwable): Resource<T> {
        throwable.let {
            it.printStackTrace()
            return when (it) {
                is HttpException -> {
                    try {
                        val mJsonObjectMsg =
                            JSONObject(it.response()!!.errorBody()!!.string())
                        Resource.Failure(
                            message = mJsonObjectMsg.optString("message"),
                            throwable = it,
                            status = it.code()
                        )

                    } catch (e1: IOException) {
                        Resource.Failure(context.getString(R.string.text_server_error_msg))
                    } catch (e1: JSONException) {
                        Resource.Failure(context.getString(R.string.text_json_exception_msg))
                    } catch (e1: Exception) {
                        Resource.Failure(context.getString(R.string.text_json_exception_msg), e1)
                    }
                }
                is SocketTimeoutException -> {
                    Resource.Failure(context.resources.getString(R.string.text_time_out_msg))
                }
                else -> {
                    Resource.Failure(
                        message = context.resources.getString(R.string.text_server_error_msg),
                        throwable = it
                    )
                }
            }
        }
    }


    /**
     * Description: This method is used for the purpose of calling api.
     *
     * @author Ankit Mishra
     * @since 22/10/21
     *
     * @param context Provide the context which is required for getting error strings from resource file.
     * @param apiFunction Provide suspend function of retrofit api call.
     *
     * @return Resource with error or data.
     */
    suspend fun <T> callApi(apiFunction: suspend () -> T): Resource<T> {
        return try {
            //Checking for the internet.
            if (MyApplication.isInternetAvailable) {
                //Calling api.
                val response = apiFunction()

                //Checking if the response was success.
                Resource.Success(response)

            } else {
                //Returning internet error if network not found.
                Resource.Failure(context.getString(R.string.internet_error), null)
            }
        } catch (e: Exception) {
            //Handling Error
            generalExceptionHandling(e)
        }
    }

    companion object {
        /**
         * Description: This method is used for checking if the status is 401.
         *
         * @author Ankit Mishra
         * @since 02/02/22
         */
        fun checkIfUnauthorized(status: Int?) = status == 401
    }
}