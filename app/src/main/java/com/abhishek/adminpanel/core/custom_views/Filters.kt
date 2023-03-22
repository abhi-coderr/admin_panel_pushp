package com.oneclick.androidcoe.core.custom_views

import android.text.InputFilter
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils

/**
  * Description: This object is used for defining input filters.
  *
  * @author Ankit Mishra
  * @since 21/01/22
  */
object Filters {

    /**
     * To disable emoji, this input filter is used.
     */
     val emojiDisabledFilter = createInputFilter { char ->
        val type = Character.getType(char)
        !(type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt())
    }

    /**
     * To disable white space, this input filter is used.
     */
     val whiteSpaceDisabledFilter = createInputFilter { char ->
        !Character.isWhitespace(char)
    }

    /**
     * Description: This method is used for the purpose of creating input filter by providing the logic.
     *
     * @author Ankit Mishra
     * @since 01/12/21
     */
    private fun createInputFilter(allowThisText: (char: Char) -> Boolean) =
        InputFilter { source, start, end, _, _, _ ->
            var keepOriginal = true
            val sb = StringBuilder(end - start)
            for (index in start until end) {
                val char = source[index]
                if (allowThisText(char)) {
                    //Called when the character is allowed.
                    sb.append(char)
                } else {
                    keepOriginal = false
                }

            }
            if (keepOriginal) {
                return@InputFilter null
            } else {
                if (source is Spanned) {
                    val sp = SpannableString(sb)
                    TextUtils.copySpansFrom(source, start, sb.length, null, sp, 0)
                    return@InputFilter sp
                } else {
                    return@InputFilter sb
                }
            }
        }

}