package com.ihfazh.jadwal_ku.screens.common

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject

class ToastHelper @Inject constructor(
    private val activity: AppCompatActivity
) {
    fun showGenericError() {
        Toast.makeText(activity, "Something bad happens", Toast.LENGTH_SHORT).show()
    }

    fun showNetworkError() {
        Toast.makeText(activity, "Network error", Toast.LENGTH_SHORT).show()
    }
}