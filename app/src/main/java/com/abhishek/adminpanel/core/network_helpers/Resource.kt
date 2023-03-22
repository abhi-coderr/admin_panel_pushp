package com.oneclick.androidcoe.core.network_helpers

/**
 * Description: This sealed class is used for the purpose of differentiating different state of api call..
 *
 * @author Ankit Mishra
 * @since 01/12/21
 */
sealed class Resource<out T> {

    /**
     * This state represents Success. You can get the data using data parameter.
     *
     * @param data Data will be sent in this.
     * @param isFromApi This will be true if the data is coming from api and false if it is cached data.
     */
    data class Success<out R>(val data: R,val isFromApi:Boolean=true) : Resource<R>()

    /**
     * This state represents Error/Failure.
     *
     * @param message Error message will be sent in this.
     * @param throwable Exception will be sent in this.
     */
    data class Failure(
        val message: String?,
        val throwable: Throwable?=null,
        val status: Int?=-1,

        ) : Resource<Nothing>()


    /**
     * This state represents Loading.
     *
     * @param showProgress true to show progress and false to hide progress.
     */
    data class Loading(val showProgress: Boolean) : Resource<Nothing>()
}
