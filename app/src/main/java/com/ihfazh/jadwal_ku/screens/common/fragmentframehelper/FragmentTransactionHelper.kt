package com.ihfazh.jadwal_ku.screens.common.fragmentframehelper

import android.view.View
import androidx.fragment.app.FragmentTransaction

class FragmentTransactionHelper {
    var sharedElementView: View? = null
    var sharedElementName: String? = null

    fun updateTransaction(transaction: FragmentTransaction): FragmentTransaction {
        if (sharedElementView != null && sharedElementName != null){
            transaction.addSharedElement(sharedElementView!!, sharedElementName!!)
        }

        return transaction
    }

    fun addSharedElement(view: View, name: String){
        sharedElementView = view
        sharedElementName = name
    }
}