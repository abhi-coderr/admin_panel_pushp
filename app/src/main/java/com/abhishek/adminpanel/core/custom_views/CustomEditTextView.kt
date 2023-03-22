package com.oneclick.blackandoneparent.core.custom_views

import android.content.Context
import android.content.res.TypedArray
import android.text.InputFilter
import android.util.AttributeSet
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.oneclick.androidcoe.core.custom_views.validators.Validator
import com.oneclick.blackandoneparent.core.TypeFactory
import com.oneclick.androidcoe.core.custom_views.Filters
import com.oneclick.blackandoneparent.R
import com.oneclick.blackandoneparent.core.MyApplication


/**
 * Description: This custom edit text view is basically edit text on steroids.
 *
 * @author Ankit Mishra
 * @since 01/12/21
 */
class CustomEditTextView(context: Context, attrs: AttributeSet?) :
    TextInputEditText(context, attrs) {
    /**
     * For font type.
     */
    private var typefaceType = TypeFactory.FontType.REGULAR.id

    /**
     * For field type. This decides the validations.
     */
    private var typeEditText = 0

    /**
     * This is comparison field id which will be required for getting comparison field.
     */
    private var comparisonFieldId = -1

    /**
     * This is comparison field which will be required for validating Confirm Password field.
     */
    private var comparisonField: CustomEditTextView? = null

    /**
     * This is error field id which will be required when there is textview for showing error.
     */
    private var errorFieldId = -1

    /**
     * This is error field text view which will be required for showing error.
     */
    private var errorField: CustomTextView? = null

    /**
     * This determines if the white space should be disabled or not.
     */
    private var isWhiteSpaceDisabled = false

    /**
     * This determines max length allowed for the text field.
     */
    private var maxLength: Int = -1


    init {
        /**
         * Getting field values from xml
         */
        getFieldValuesFromXml(context, attrs)

        if (!isInEditMode) {
            setTextIsSelectable(false)
            typeface = MyApplication.getTypeFace(TypeFactory.FontType.getFontType(typefaceType))
        }

        onFocusChangeListener = OnFocusChangeListener { _, _ ->
            if (!hasFocus()) {
                isValid()
            }
        }

        //Setting up the filters
        setDefaultFilters()

    }

    /**
     * Description: This method is used for the purpose of getting xml field values.
     *
     * @author Ankit Mishra
     * @since 01/12/21
     *
     * @param context Provide context.
     * @param attrs Provide attribute set or null.
     */
    private fun getFieldValuesFromXml(context: Context, attrs: AttributeSet?) {
        try {
            val array: TypedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.CustomEditTextView,
                0, 0
            )
            //This attribute will be required for confirm password field.
            comparisonFieldId =
                array.getResourceId(R.styleable.CustomEditTextView_comparison_field, -1)
            typefaceType = array.getInteger(
                R.styleable.CustomTextView_font_name,
                TypeFactory.FontType.REGULAR.id
            )
            errorFieldId = array.getResourceId(
                R.styleable.CustomEditTextView_error_field,
                -1
            )
            isWhiteSpaceDisabled =
                array.getBoolean(R.styleable.CustomEditTextView_space_disabled, false)
            maxLength = array.getInteger(R.styleable.CustomEditTextView_max_number_of_digits, -1)
            typeEditText = array.getInteger(R.styleable.CustomEditTextView_field_type, typeEditText)

            array.recycle()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Description: This method is used for setting up the field type.
     *
     * @author Ankit Mishra
     * @since 21/01/22
     */
    fun setFieldType(fieldType: Int) {
        typeEditText = fieldType
    }

    /**
     * Description: This method is used for the purpose of setting default filters.
     *
     * @author Ankit Mishra
     * @since 01/12/21
     */
    private fun setDefaultFilters() {
        val filtersList = arrayListOf(Filters.emojiDisabledFilter)
        if (isWhiteSpaceDisabled) {
            filtersList.add(Filters.whiteSpaceDisabledFilter)
        }
        if (maxLength != -1) {
            filtersList.add(InputFilter.LengthFilter(maxLength))
        }
        filters = filtersList.toTypedArray()
    }

    /**
     * Description: This method is used for the purpose of setting up the input filters.
     *
     * @author Ankit Mishra
     * @since 01/12/21
     *
     * @param filtersApplied Provide the filters which you needed to apply.
     */
    fun setFilters(filtersApplied: ArrayList<InputFilter>) {
        val filtersList = arrayListOf(Filters.emojiDisabledFilter)
        filtersList.addAll(filtersApplied)
        filters = filtersList.toTypedArray()
    }

    /**
     * Description: This method is used for the purpose of showing error message.
     *
     * @author Ankit Mishra
     * @since 01/12/21
     *
     * @param error Provide the error message.
     */
    private fun showError(error: String) {
        if (errorField == null) {
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        } else {
            errorField?.isVisible = true
            errorField?.text = error
        }
    }

    /**
     * Description: This method is used for the purpose of checking if this edit text field is valid or not.
     *
     * @author Ankit Mishra
     * @since 01/12/21
     */
    private fun isValid(): Boolean {
        /**
         * Getting error message if validation returns false.
         */
        val errorMessage = Validator.getErrorMessageIfInvalid(
            typeEditText,
            text.toString().trim(),
            comparisonField?.text?.trim()?.toString()
        )
        return if (errorMessage.trim().isBlank()) {
            errorField?.isVisible = false
            true
        } else {
            showError(errorMessage)
            false
        }
    }

    companion object {
        /**
         * Description: This method is used for the purpose of checking if all the provided edit text fields data is valid.
         *
         * @author Ankit Mishra
         * @since 01/12/21
         *
         * @param list Provide the list of CustomEditTextView which are needed to be validated.
         * @param shouldCheckAllTheFields Provide true if you want to validate all fields even if we found the error in some field.
         */
        fun areAllTheseEditTextsValid(
            list: ArrayList<CustomEditTextView>,
            shouldCheckAllTheFields: Boolean = true
        ): Boolean {
            var result = true
            for (i in list.indices) {
                if (!list[i].isValid()) {
                    result = false
                    if (!shouldCheckAllTheFields) {
                        return result
                    }
                }
            }
            return result
        }

    }

    /**
     * This method is needed to get views from the provided id.
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        try {
            //Below logic is introduced for the confirm password field.
            if (comparisonFieldId != -1) {
                comparisonField =
                    (this.rootView as View).findViewById(comparisonFieldId)

            }
            //Below logic is introduced for the text input layout field.
            if (errorFieldId != -1) {
                errorField = (this.rootView as View).findViewById(errorFieldId)

                errorField?.let {
                    addTextChangedListener {
                        isValid()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}

/**
 * Description: This binding adapter is used for setting up the field type.
 *
 * @author Ankit Mishra
 * @since 21/01/22
 */
@BindingAdapter("field_type")
fun setFieldType(view: CustomEditTextView, fieldType: Int) {
    view.setFieldType(fieldType)
}