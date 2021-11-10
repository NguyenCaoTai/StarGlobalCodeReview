package com.example.myapplication.ui.main.utilities

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object DataBindingAdapter {
    @JvmStatic
    @BindingAdapter("imageUrl", "error")
    fun loadImage(
        view: ImageView,
        url: String?,
        error: Drawable?
    ) {
        Glide.with(view.context)
            .load(url)
            .placeholder(error)
            .circleCrop()
            .into(view)
    }
}