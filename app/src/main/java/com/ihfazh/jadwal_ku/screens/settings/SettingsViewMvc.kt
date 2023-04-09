package com.ihfazh.jadwal_ku.screens.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.ihfazh.jadwal_ku.R
import com.ihfazh.jadwal_ku.screens.common.views.BaseMvcView

class SettingsViewMvc(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?
): BaseMvcView<SettingsViewMvc.Listener>(layoutInflater, parent, R.layout.layout_settings) {

    interface Listener {
        fun onLogoutClicked()
    }

    private val version: TextView = findViewById(R.id.txtVersion)
    private val btnLogout: MaterialButton = findViewById(R.id.btnLogout)

    init {
        btnLogout.setOnClickListener {
            listeners.forEach { listener ->  listener.onLogoutClicked() }
        }
    }

    fun setVersion(versionStr: String){
        version.text = versionStr
    }
}