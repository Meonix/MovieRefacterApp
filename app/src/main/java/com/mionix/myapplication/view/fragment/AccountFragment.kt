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
import kotlinx.android.synthetic.main.fragment_account.*

/**
 * A simple [Fragment] subclass.
 */
class AccountFragment : Fragment() {
    private var mAuth: FirebaseAuth? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val accountFragment = inflater.inflate(R.layout.fragment_account, container, false)

        return accountFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        btLogin!!.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
        btLogout!!.setOnClickListener {
            mAuth!!.signOut()
            LoginManager.getInstance().logOut()
            Toast.makeText(context,"Log out Success..", Toast.LENGTH_SHORT).show()
            btLogout!!.visibility = View.INVISIBLE
            btLogin!!.visibility = View.VISIBLE
        }
    }

    private fun initView(accountFragment: View) {
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth!!.currentUser
        if(currentUser != null ){
            btLogin!!.visibility = View.INVISIBLE
            btLogout!!.visibility = View.VISIBLE
        }
        else{
            btLogin!!.visibility = View.VISIBLE
            btLogout!!.visibility = View.INVISIBLE
        }
    }


}
