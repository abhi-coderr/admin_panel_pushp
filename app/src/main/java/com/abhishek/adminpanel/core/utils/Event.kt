package com.oneclick.blackandoneparent.core.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}
/**
 * Description: This method is used for the purpose of reducing the event content handling code.
 *
 * @author Ankit Mishra
 * @since 07/12/21
 *
 * @param doSomethingWithData Provide function which will be executed if the event is handled for the first time.
 */
fun <T> Event<T>.getEventContentIfNotHandled(doSomethingWithData:(T)->Unit) {
    getContentIfNotHandled()?.let{
        doSomethingWithData(it)
    }
}


fun <T> LiveData<Event<T>>.observeNotHandledEvent(
    owner: LifecycleOwner,
    onEventData: (T) -> Unit
) {
    observe(owner
    ) { event ->
        event.getEventContentIfNotHandled { eventData ->
            onEventData(
                eventData
            )
        }
    }
}



