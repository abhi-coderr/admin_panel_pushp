package com.oneclick.blackandoneparent.core.utils

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.oneclick.blackandoneparent.R
import com.oneclick.blackandoneparent.core.custom_views.CustomTextView
import java.util.*

@BindingAdapter("tint")
fun ImageView.setImageTint(@ColorInt color: Int) {
    setColorFilter(color)
}

@BindingAdapter("loadImageUsingGlide")
fun loadImage(view: ImageView, imageUrl: String?) {

    Glide.with(view.context)
        .load(imageUrl)
        .error(R.drawable.ic_launcher_foreground)
        .timeout(60000)
        .placeholder(R.drawable.ic_launcher_foreground)
        .into(view)

}

@SuppressLint("DefaultLocale")
@BindingAdapter("textCapitalize")
fun textCapitalize(view: CustomTextView, textReceived: String?) {
    val text = textReceived.orEmpty()
    view.text = text.lowercase(Locale.ROOT).capitalized()

}
