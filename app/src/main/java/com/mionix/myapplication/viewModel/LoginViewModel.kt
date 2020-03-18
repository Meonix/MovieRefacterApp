package com.mionix.myapplication.viewModel

import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.mionix.myapplication.base.BaseViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern


class LoginViewModel : BaseViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    private val _isLoginSucess = MutableLiveData<Boolean>()
    private val _emailError = MutableLiveData<String>()
    private val _messageLoginError = MutableLiveData<String>()
    private val _passwordError = MutableLiveData<String>()
    private var mAuth = FirebaseAuth.getInstance()
    private var usersRef = FirebaseDatabase.getInstance().reference.child("Users")

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val isLoginSucess: LiveData<Boolean>
        get() = _isLoginSucess

    val emailError: LiveData<String>
        get() = _emailError

    val messageLoginError: LiveData<String>
        get() = _messageLoginError

    val passwordError: LiveData<String>
        get() = _passwordError

    fun signUp(email: String, password: String){
        if(isValidInput(email, password)) {
            _isLoading.value = true
            viewModelScope.launch {
                _isLoading.value = true
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val CurrentUserID = mAuth.currentUser!!.uid
                        val deviceToken = FirebaseInstanceId.getInstance().token

                        usersRef.child(CurrentUserID).child("device_token")
                            .setValue(deviceToken)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    _isLoginSucess.value = true
                                    _isLoading.value = false
                                }
                            }
                    } else {
                        val message = task.exception!!.toString()
                        _messageLoginError.value = message
                        _isLoginSucess.value = false
                        _isLoading.value = false
                    }
                }
            }
        }
    }
    private fun isValidInput(email: String, password: String): Boolean{
        if(email.isEmpty()){
            _emailError.value = "Email is empty"
            return false
        }else if(email.isNotEmpty() && !isEmailValid(email)){
            _emailError.value = "Email invalidate"
            return false
        }else if(password.isEmpty()){
            _passwordError.value = "Password is empty"
            return false
        }
        return true
    }
    //Regular Expression
    private fun isEmailValid(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }
}
