package org.nasa.apod.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide

@BindingAdapter("dailyHdImage")
fun loadImage(view: ImageView, imageUrl: String?) {
    Glide.with(view.context)
        .load(imageUrl)
        .thumbnail(0.5f)
        .placeholder(createPlaceHolder(view.context))
        .override(view.width, view.height)
        .into(view)
}


fun createPlaceHolder(context: Context): CircularProgressDrawable {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    return circularProgressDrawable
}
