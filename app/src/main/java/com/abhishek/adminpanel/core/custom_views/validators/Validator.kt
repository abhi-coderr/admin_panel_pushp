package com.oneclick.androidcoe.core.custom_views.validators

import android.util.Patterns

/**
 * Description: This class is used for the purpose of validating text fields.
 *
 * @author Ankit Mishra
 * @since 01/12/21
 */
object Validator {

    /**
     * This class is used for the purpose of representing single validation.
     * @param validationFunc Provide function which accepts the text and returns true if the text is valid else false.
     * @param errorMessage Provide the error message which will be return when the text is not valid.
     */
    data class SingleValidation(
        val validationFunc: ((text: String) -> Boolean),
        val errorMessage: String
    )

    /**
     * This class is used for the purpose of representing comparison validation.
     * @param validationFunc Provide function which accepts the text and returns true if the text is valid else false.
     * @param errorMessage Provide the error message which will be return when the text is not valid.
     */
    data class ComparisonValidation(
        val validationFunc: ((text: String, otherText: String) -> Boolean),
        val errorMessage: String
    )

    /**
     * This is validations map. It contains all the validation for different type of fields.
     */
    private val validations: MutableMap<Int, List<SingleValidation>> =
        mutableMapOf()

    /**
     * This is comparison validations map. It contains all the validation for different type of fields.
     */
    private val comparisonValidations: MutableMap<Int, List<ComparisonValidation>> =
        mutableMapOf()


    /**
     * Description: This method returns SingleValidation object to check if the edit text field is empty or not.
     *
     * @author Ankit Mishra
     * @since 01/12/21
     *
     * @param errorMessage Provide the error message that will be shown if the text field is empty.
     */
     fun validationForNotEmpty(errorMessage: String) = SingleValidation(
        { text -> text.isNotBlank() }, errorMessage
    )

    /**
     * Description: This method is used for the purpose of checking if the email is valid or not.
     *
     * @author Ankit Mishra
     * @since 01/12/21
     *
     * @param email Provide the email which needed to be validated.
     */
     fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
      * Description: This method is used for setting up the custom validation for given type parameter.
      *
      * @author Ankit Mishra
      * @since 21/01/22
     *
     * @param type Provide unique type integer.
     * @param singleValidations Provide the list of single validations.
     * @param comparisonValidations Provide the list of comparison validations.
      */
    fun setValidation(
        type: Int,
        singleValidations: List<SingleValidation>,
        comparisonValidations: List<ComparisonValidation>?=null
    )=apply {
        validations[type] = singleValidations
        comparisonValidations?.let {
            Validator.comparisonValidations[type] = comparisonValidations
        }
    }



    /**
     * Description: This method is used for the purpose of getting error message if the field data is invalid.
     *
     * @author Ankit Mishra
     * @since 01/12/21
     *
     * @param type Provide the Type of text field.
     * @param text Provide the text of text field.
     * @param otherText Provide the other field's text which needed to be compared with text. By Default this is null.
     *
     */
    fun getErrorMessageIfInvalid(type: Int, text: String, otherText: String? = null): String {
        val validations = validations[type]
        if (validations.isNullOrEmpty()) return String()//This means no validations are required.

        for (validation in validations) {
            if (!validation.validationFunc(text)) {
                return validation.errorMessage
            }
        }
        otherText?.let {
            val comparisonValidations = comparisonValidations[type]
            comparisonValidations?.let {
                for (comparisonValidation in comparisonValidations) {
                    if (!comparisonValidation.validationFunc(text, otherText)) {
                        return comparisonValidation.errorMessage
                    }
                }
            }
        }

        return String()
    }

}