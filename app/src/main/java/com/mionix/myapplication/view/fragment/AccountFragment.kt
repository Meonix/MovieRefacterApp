package com.mionix.myapplication.view.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth

import com.mionix.myapplication.R
import com.mionix.myapplication.view.LoginActivity

/**
 * A simple [Fragment] subclass.
 */
class AccountFragment : Fragment() {
    private lateinit var btLogin : Button
    private lateinit var btLogout: Button
    private var mAuth: FirebaseAuth? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val accountFragment = inflater.inflate(R.layout.fragment_account, container, false)
        initView(accountFragment)
        btLogin.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
        btLogout.setOnClickListener {
            mAuth!!.signOut()
            LoginManager.getInstance().logOut()
            Toast.makeText(context,"Log out Success..", Toast.LENGTH_SHORT).show()
            btLogout.visibility = View.INVISIBLE
            btLogin.visibility = View.VISIBLE
        }
        return accountFragment
    }

    private fun initView(accountFragment: View) {
        btLogin = accountFragment.findViewById(R.id.btLogin)
        btLogout = accountFragment.findViewById(R.id.btLogout)
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth!!.currentUser
        if(currentUser != null ){
            btLogin.visibility = View.INVISIBLE
            btLogout.visibility = View.VISIBLE
        }
        else{
            btLogin.visibility = View.VISIBLE
            btLogout.visibility = View.INVISIBLE
        }
    }


}
