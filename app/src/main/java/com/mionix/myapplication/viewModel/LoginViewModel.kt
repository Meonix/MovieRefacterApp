package com.mionix.myapplication.viewModel

import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.iid.FirebaseInstanceId


class LoginViewModel(private var email: String, private var password: String) : ViewModel() {
    fun checkInput() : Boolean{
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            return false
        }
        return true
    }
}
