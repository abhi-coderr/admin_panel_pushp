package com.oneclick.blackandoneparent.core.custom_views

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.oneclick.blackandoneparent.core.TypeFactory
import com.oneclick.blackandoneparent.R
import com.oneclick.blackandoneparent.core.MyApplication

class CustomTextView(context: Context, attrs: AttributeSet?) :
    AppCompatTextView(context, attrs) {
    private var typefaceType = TypeFactory.FontType.REGULAR.id

    init {
        val array: TypedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomTextView,
            0, 0
        )

        typefaceType = try {
            array.getInteger(R.styleable.CustomTextView_font_name, TypeFactory.FontType.REGULAR.id)
        } finally {
            array.recycle()
        }

        if (!isInEditMode) {
            setTextIsSelectable(false)
            typeface = MyApplication.getTypeFace(TypeFactory.FontType.getFontType(typefaceType))
        }
    }
}
