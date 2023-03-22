package com.oneclick.blackandoneparent.core.custom_views.validators

import android.content.Context
import com.oneclick.androidcoe.core.custom_views.validators.Validator
import com.oneclick.androidcoe.core.custom_views.validators.Validator.validationForNotEmpty
import com.oneclick.blackandoneparent.R


/**
 * Description: This object is used for separating the App Level Validation logic from the Custom Edit Text View Class.
 *
 * @author Ankit Mishra
 * @since 21/01/22
 */
object MyValidations {

    /**
     * This enum class represents the edit text type.
     * @param value Provide the value it is in the Custom Edit Text View.
     */
    enum class FieldType(val value: Int) {
        EMAIL(1),
        PASSWORD(2),
        CONFIRM_PASSWORD(3),
        PHONE_NUMBER(4),
        FIRST_NAME(5),
        LAST_NAME(6);
    }

    /**
     * Description: This method is used for setting up the text validations for all the edit texts.
     *
     * @author Ankit Mishra
     * @since 21/01/22
     */
    fun setAllEditTextValidations(context: Context) {
        Validator.setValidation(
            FieldType.LAST_NAME.value,
            listOf(
//                validationForNotEmpty(context.getString(R.string.text_register_empty_lname)),
//                Validator.SingleValidation(
//                    validationFunc = { text -> text.length >= 3 },
//                    errorMessage = context.getString(R.string.text_register_valid_lname)
//                ),
//                Validator.SingleValidation(
//                    validationFunc = { text -> text.length <= 30 },
//                    errorMessage = context.getString(R.string.text_register_valid_lname_max_length)
//                )
            )
        )

    }
}