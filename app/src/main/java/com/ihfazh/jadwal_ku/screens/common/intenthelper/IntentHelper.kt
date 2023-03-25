package com.ihfazh.jadwal_ku.screens.common.intenthelper

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject

class IntentHelper @Inject constructor(
    private val activity: AppCompatActivity
) {
    fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun startActivity(intent: Intent) {
        activity.startActivity(intent)
    }
}