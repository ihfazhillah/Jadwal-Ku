package com.ihfazh.jadwal_ku.screens.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.ihfazh.jadwal_ku.R
import com.ihfazh.jadwal_ku.screens.common.toolbar.ToolbarMvcView
import com.ihfazh.jadwal_ku.screens.common.views.ObservableMvcView

class SettingsViewMvc(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?
): ObservableMvcView<SettingsViewMvc.Listener>(layoutInflater, parent, R.layout.layout_settings) {

    interface Listener {
        fun onLogoutClicked()
    }

    private val version: TextView = findViewById(R.id.txtVersion)
    private val btnLogout: MaterialButton = findViewById(R.id.btnLogout)
    private val toolbar: FrameLayout = findViewById(R.id.toolbar)

    init {
        btnLogout.setOnClickListener {
            listeners.forEach { listener ->  listener.onLogoutClicked() }
        }

        val toolbarViewMvc = ToolbarMvcView(layoutInflater, parent)
        toolbarViewMvc.setTitle("Settings")
        toolbar.addView(toolbarViewMvc.rootView)
    }

    fun setVersion(versionStr: String){
        version.text = versionStr
    }
}