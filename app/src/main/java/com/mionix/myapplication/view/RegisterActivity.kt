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
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.mionix.myapplication.R
import com.mionix.myapplication.viewModel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var rootRef: DatabaseReference
    private lateinit var loadingBar: ProgressDialog
    private val registerViewModel: RegisterViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()
        rootRef = FirebaseDatabase.getInstance().reference
        initializeFields()
        setupViewModel()
        already_have_account_link.setOnClickListener { sendUserToLoginActivity() }
        register_button.setOnClickListener {
            loadingBar.setTitle("Creating New Account")
            loadingBar.setMessage("Please wait, while we are creating new account for you...")
            loadingBar.setCanceledOnTouchOutside(true)
            loadingBar.show()
            createNewAccount() }



    }

    private fun setupViewModel() {
        registerViewModel.isCreateSucess.observe(this, Observer {
            if(it){
                sendUserToProfileActivity()
                Toast.makeText(this@RegisterActivity, "Account created Successfully...", Toast.LENGTH_SHORT).show()
                loadingBar.dismiss()
            }else{
                registerViewModel.messageCreateError.observe(this, Observer {e->
                    Toast.makeText(this@RegisterActivity, "Error :$e", Toast.LENGTH_SHORT).show()
                    loadingBar.dismiss()
                })

            }
        })
    }

    private fun createNewAccount() {
        registerViewModel.createAccount(register_email.text.trim().toString()
            ,register_password.text.trim().toString())
    }

    private fun initializeFields() {
        loadingBar = ProgressDialog(this)
        setSupportActionBar(register_toolbar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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