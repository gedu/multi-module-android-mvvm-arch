package com.teddy.brunch.base

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.teddy.brunch.R
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    view.load(url) {
        crossfade(true)
        placeholder(R.drawable.placeholder_pic)
    }
}

@BindingAdapter("android:isVisible")
fun View.setIsVisible(visible: Boolean?) {
    visibility = if (visible != null && visible) View.VISIBLE else View.GONE
}

@BindingAdapter("datePrefix")
fun loadDate(view: TextView, preFix: String?) {
    val simpleDateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    val todayDate = simpleDateFormat.format(Date())
    view.text = "${preFix ?: ""}$todayDate"
}