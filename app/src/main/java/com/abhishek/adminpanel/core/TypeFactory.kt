package com.oneclick.blackandoneparent.core

import android.content.Context
import android.graphics.Typeface

/**
 * Description: This type factory class is used for the purpose of providing custom typefaces.
 *
 * @author Ankit Mishra
 * @since 14/02/22
 *
 * @param context Provide context.
 */
class TypeFactory(context: Context) {

    /**
     * Description: This enum class is used for getting different fonts.
     *
     * @author Ankit Mishra
     * @since 11/02/22
     */
    enum class FontType(val id: Int, val fontPath: String) {
        REGULAR(1, "Poppins-Regular.otf"),
        MEDIUM(2, "Poppins-Medium.otf"),
        SEMI_BOLD(3, "Poppins-SemiBold.otf");

        companion object {
            /**
             * Description: This method is used for getting font type from id.
             *
             * @author Ankit Mishra
             * @since 11/02/22
             *
             * @param id Provide id for font type.
             */
            fun getFontType(id: Int) = values().find { it.id == id } ?: REGULAR
        }
    }

    var regular: Typeface = Typeface.createFromAsset(context.assets, FontType.REGULAR.fontPath)
    var medium: Typeface = Typeface.createFromAsset(context.assets, FontType.MEDIUM.fontPath)
    var semibold: Typeface = Typeface.createFromAsset(context.assets, FontType.SEMI_BOLD.fontPath)

}
