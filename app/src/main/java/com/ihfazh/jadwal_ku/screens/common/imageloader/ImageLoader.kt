package com.ihfazh.jadwal_ku.screens.common.imageloader

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import javax.inject.Inject

class ImageLoader @Inject constructor(private val activity: AppCompatActivity) {

    private val requestOptions = RequestOptions().centerCrop()

    fun loadImage(imageUrl: String, target: ImageView) {
        Glide.with(activity).load(imageUrl).apply(requestOptions).into(target)
    }

    @SuppressLint("CheckResult")
    fun loadImageCenterInside(imageUrl: String, target: ImageView){
        Glide.with(activity).load(imageUrl).apply {
            centerInside()
        }
            .into(target)
    }
}

