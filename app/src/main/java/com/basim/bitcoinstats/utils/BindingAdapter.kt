package com.basim.bitcoinstats.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter

/**
 * All UI data binding adapters comes under this class
 */
@BindingAdapter("iconId")
fun loadImage(view: ImageView, id: Int?) {
    view.setImageDrawable(view.context.getDrawable(id!!))
}