package com.ihfazh.jadwal_ku.screens.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.ihfazh.jadwal_ku.R
import com.ihfazh.jadwal_ku.authentication.LoginData
import com.ihfazh.jadwal_ku.screens.common.views.ObservableMvcView


class LoginViewMvc(
    inflater: LayoutInflater,
    parent: ViewGroup?
) : ObservableMvcView<LoginViewMvc.Listener>(inflater, parent, R.layout.layout_login) {

    interface Listener {
        fun onLoginClick()
    }

    private val edtUsernameLayout: TextInputLayout = findViewById(R.id.edtUsernameLayout)
    private val edtPasswordLayout: TextInputLayout = findViewById(R.id.edtPasswordLayout)

    private val edtUsername: TextInputEditText = findViewById(R.id.edtUsername)
    private val edtPassword: TextInputEditText = findViewById(R.id.edtPassword)
    private val btnLogin: MaterialButton = findViewById(R.id.btnLogin)

    private val txtError: TextView = findViewById(R.id.txtError)

    init {
        btnLogin.setOnClickListener {
            listeners.forEach { listener -> listener.onLoginClick() }
        }

        edtPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO){
                listeners.forEach { listener -> listener.onLoginClick() }
                true
            } else {
                false
            }
        }
    }

    fun getLoginData(): LoginData {
        val username = edtUsername.text.toString()
        val password = edtPassword.text.toString()
        return LoginData(username, password)
    }

    fun setUsernameError(error: String){
        edtUsernameLayout.error = error
    }

    fun setPasswordError(error: String){
        edtPasswordLayout.error = error
    }

    fun setLoggingIn(flag: Boolean){
        btnLogin.isEnabled = flag
    }

    fun setAndDisplayGlobalError(error: String){
        txtError.text = error
        txtError.visibility = View.VISIBLE
    }

    fun hideGlobalError(){
        txtError.visibility = View.GONE
    }

    fun hideUsernameError() {
        edtUsernameLayout.error = ""
    }

    fun hidePasswordError(){
        edtPasswordLayout.error = ""
    }

    fun disableLoginButton() {
        btnLogin.isEnabled = false
    }

    fun enableLoginButton() {
        btnLogin.isEnabled = true
    }

}