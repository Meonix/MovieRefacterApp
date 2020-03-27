package com.mionix.myapplication.viewModel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.mionix.myapplication.base.BaseViewModel
import java.util.regex.Pattern

class RegisterViewModel: BaseViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    private val _isCreateSucess = MutableLiveData<Boolean>()
    private val _isLoginGoogleSucess = MutableLiveData<Boolean>()
    private val _emailError = MutableLiveData<String>()
    private val _emailUserName = MutableLiveData<String>()
    private val _messageCreateError = MutableLiveData<String>()
    private val _passwordError = MutableLiveData<String>()
    private var mAuth = FirebaseAuth.getInstance()
    private var rootRef = FirebaseDatabase.getInstance().reference

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val isCreateSucess: LiveData<Boolean>
        get() = _isCreateSucess

    val isLoginGoogleSucess: LiveData<Boolean>
        get() = _isLoginGoogleSucess

    val emailError: LiveData<String>
        get() = _emailError

    val emailUserName: LiveData<String>
        get() = _emailUserName

    val messageCreateError: LiveData<String>
        get() = _messageCreateError

    val passwordError: LiveData<String>
        get() = _passwordError

    fun createAccount(email: String, password: String){
        if(isValidInput(email,password)){
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val deviceToken = FirebaseInstanceId.getInstance().token


                    val currentUserID = mAuth.currentUser?.uid
                    rootRef.child("Users").child(currentUserID.toString()).setValue("")

                    rootRef.child("Users").child(currentUserID.toString()).child("device_token")
                        .setValue(deviceToken)
                    _isCreateSucess.value = true

                } else {
                    _messageCreateError.value = task.exception.toString()
                    _isCreateSucess.value =false
                }
            }
        }
        else{
            _messageCreateError.value = "Failed to create"
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