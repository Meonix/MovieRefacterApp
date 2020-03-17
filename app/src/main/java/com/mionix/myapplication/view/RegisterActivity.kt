package com.mionix.myapplication.view

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.mionix.myapplication.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var rootRef: DatabaseReference? = null
    private var loadingBar: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()
        rootRef = FirebaseDatabase.getInstance().reference
        initializeFields()

        already_have_account_link!!.setOnClickListener { sendUserToLoginActivity() }
        register_button!!.setOnClickListener { createNewAccount() }



    }

    private fun createNewAccount() {
        val email = register_email!!.text.toString()
        val password = register_password!!.text.toString()
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email....", Toast.LENGTH_SHORT).show()
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password....", Toast.LENGTH_SHORT).show()
        } else {
            loadingBar!!.setTitle("Creating New Account")
            loadingBar!!.setMessage("Please wait, while we are creating new account for you...")
            loadingBar!!.setCanceledOnTouchOutside(true)
            loadingBar!!.show()

            mAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val deviceToken = FirebaseInstanceId.getInstance().token


                    val currentUserID = mAuth!!.currentUser!!.uid
                    rootRef!!.child("Users").child(currentUserID).setValue("")

                    rootRef!!.child("Users").child(currentUserID).child("device_token")
                        .setValue(deviceToken)
                    sendUserToProfileActivity()
                    Toast.makeText(this@RegisterActivity, "Account created Successfully...", Toast.LENGTH_SHORT).show()
                    loadingBar!!.dismiss()
                } else {
                    val message = task.exception!!.toString()
                    Toast.makeText(this@RegisterActivity, "Error :$message", Toast.LENGTH_SHORT).show()
                    loadingBar!!.dismiss()
                }
            }
        }
    }

    private fun initializeFields() {
        loadingBar = ProgressDialog(this)
        setSupportActionBar(register_toolbar as Toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun sendUserToLoginActivity() {
        val loginIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(loginIntent)
    }

    private fun sendUserToProfileActivity() {
        val mainIntent = Intent(this@RegisterActivity, MainActivity::class.java)
        startActivity(mainIntent)
    }
}