package com.mionix.myapplication.view

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.widget.Toolbar
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.linecorp.linesdk.LineApiResponseCode
import com.linecorp.linesdk.LoginDelegate
import com.linecorp.linesdk.LoginListener
import com.linecorp.linesdk.Scope
import com.linecorp.linesdk.auth.LineAuthenticationParams
import com.linecorp.linesdk.auth.LineLoginApi
import com.linecorp.linesdk.auth.LineLoginResult
import com.linecorp.linesdk.widget.LoginButton
import com.mionix.myapplication.R
import com.mionix.myapplication.viewModel.LoginViewModel
import java.util.*

class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var UsersRef: DatabaseReference? = null
    private var currentUser: FirebaseUser? = null
    private var UserEmail: EditText? = null
    private var registerButton: Button? = null
    private var loginButton: Button? = null
    private var loadingBar: ProgressDialog? = null
    private var UserPassword: EditText? = null
    private var toolbar : Toolbar? = null
    private val REQUEST_CODE = 1
    private val RC_SIGN_IN = 2
    private lateinit var callbackManager: CallbackManager
    private var TAG :String = "FACELOG"
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var gso : GoogleSignInOptions
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        UsersRef = FirebaseDatabase.getInstance().reference.child("Users")

        InitializeFields()
        currentUser = mAuth!!.currentUser
        loginButton!!.setOnClickListener{ AllowUserToLogin() }

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)
        InitLineLoginButton()
        InitFacebookLoginButton()
        InitGoogleLoginButton()
        // check Hash function in computer facebook SDK
//        try {
//            val info = packageManager.getPackageInfo(
//                "com.mionix.myapplication",
//                PackageManager.GET_SIGNATURES
//            )
//            for (signature in info.signatures) {
//                val md = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                Log.d("KeyHash:", android.util.Base64
//                    .encodeToString(md.digest(), android.util.Base64.DEFAULT))
//            }
//        } catch (e: PackageManager.NameNotFoundException) {
//
//        } catch (e: NoSuchAlgorithmException) {
//
//        }
        registerButton!!.setOnClickListener{ SendUserToRegisterActivity() }
    }

    private fun SendUserToRegisterActivity() {
        val registerIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(registerIntent)
    }

    private fun InitializeFields() {
        registerButton = findViewById(R.id.btRegister)
        loginButton =findViewById(R.id.btLogin)
        UserEmail = findViewById(R.id.tietEmailLogin)
        UserPassword = findViewById(R.id.tietPasswordLogin)
        toolbar = findViewById(R.id.login_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        loadingBar = ProgressDialog(this)
    }
    private fun AllowUserToLogin() {
        val email = UserEmail!!.text.toString()
        val password = UserPassword!!.text.toString()
        loginViewModel = LoginViewModel(email,password)
        if (!loginViewModel.checkInput()) {
            Toast.makeText(this, "Please complete all information....", Toast.LENGTH_SHORT).show()
        } else {
            loadingBar!!.setTitle("Sign In")
            loadingBar!!.setMessage("Please wait....")
            loadingBar!!.setCanceledOnTouchOutside(false)
            loadingBar!!.show()

            mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val CurrentUserID = mAuth!!.currentUser!!.uid
                    val deviceToken = FirebaseInstanceId.getInstance().token

                    UsersRef!!.child(CurrentUserID).child("device_token")
                        .setValue(deviceToken)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                SendUserToProfileActivity()
                                Toast.makeText(this@LoginActivity, "Logged in  Successful....", Toast.LENGTH_SHORT).show()
                                loadingBar!!.dismiss()
                            }
                        }
                } else {
                    val message = task.exception!!.toString()
                    Toast.makeText(this@LoginActivity, "Error :$message", Toast.LENGTH_SHORT).show()
                    loadingBar!!.dismiss()
                }
            }
        }
    }
    private fun SendUserToProfileActivity() {
        val mainIntent = Intent(this@LoginActivity, MainActivity::class.java)
        Toast.makeText(this,"login success..",Toast.LENGTH_SHORT).show()
        startActivity(mainIntent)
    }
    private fun InitGoogleLoginButton(){
        val signIn = findViewById(R.id.btGoogleLogin) as SignInButton
        signIn.setOnClickListener {
                view: View? -> signGoogle()
        }
    }
    private fun signGoogle() {
        val signIntent:Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signIntent,RC_SIGN_IN)
    }

    private fun InitLineLoginButton(){
        val loginDelegate = LoginDelegate.Factory.create()
        val loginButton = findViewById(R.id.line_login_btn) as LoginButton
        loginButton.setChannelId("1653882343")
// configure whether login process should be done by Line App, or inside WebView.
        loginButton.enableLineAppAuthentication(true)

// set up required scopes and nonce.
        loginButton.setAuthenticationParams(
            LineAuthenticationParams.Builder()
                .scopes(Arrays.asList(Scope.PROFILE))
                // .nonce("<a randomly-generated string>") // nonce can be used to improve security
                .build()
        )
        loginButton.setLoginDelegate(loginDelegate)
        loginButton.addLoginListener(object : LoginListener {
            override fun onLoginSuccess(result: LineLoginResult) {
                Toast.makeText(this@LoginActivity, "Login success", Toast.LENGTH_SHORT).show()

            }

            override fun onLoginFailure(@Nullable result: LineLoginResult?) {
                Toast.makeText(this@LoginActivity, "Login failure", Toast.LENGTH_SHORT).show()
            }
        })

        loginButton.setOnClickListener{
            try {
                // App-to-app login
                val loginIntent = LineLoginApi.getLoginIntent(
                    this,
                    "1653882343",
                    LineAuthenticationParams.Builder()
                        .scopes(Arrays.asList(Scope.PROFILE))
                        // .nonce("<a randomly-generated string>") // nonce can be used to improve security
                        .build()
                )
                startActivityForResult(loginIntent, REQUEST_CODE)

            } catch (e: Exception) {
                Log.e("ERROR", e.toString())
            }

        }
    }
    private fun InitFacebookLoginButton(){
        callbackManager = CallbackManager.Factory.create()
        val btFacebookLogin = findViewById(R.id.btFacebookLogin) as com.facebook.login.widget.LoginButton
        btFacebookLogin.setReadPermissions("email", "public_profile")
        btFacebookLogin.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                handleFacebookAccessToken(loginResult.accessToken)
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            }

            override fun onCancel() {
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(applicationContext, "Error$error",Toast.LENGTH_SHORT).show()

            }
        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN){
            val task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleGoogleLoginResult(task)
        }
        if (requestCode != REQUEST_CODE) {
            Log.e("ERROR", "Unsupported Request")
            return
        }
        else if (requestCode == REQUEST_CODE){
           val result : LineLoginResult = LineLoginApi.getLoginResultFromIntent(data)
            if(result.responseCode == LineApiResponseCode.SUCCESS){
                val accessToken = result.lineCredential!!.accessToken.tokenString
                val transitionIntent = Intent(this@LoginActivity, MainActivity::class.java)
                Toast.makeText(this,"login success..",Toast.LENGTH_SHORT).show()
                transitionIntent.putExtra("line_profile", result.lineProfile)
                transitionIntent.putExtra("line_credential", result.lineCredential)
                transitionIntent.putExtra("line_result", result)
                Toast.makeText(this,"welcome  "+result.lineProfile!!.displayName,Toast.LENGTH_LONG).show()
                startActivity(transitionIntent)
            }
            else if (result.responseCode == LineApiResponseCode.CANCEL){
//                Toast.makeText(this,result.errorData.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleGoogleLoginResult(task : Task<GoogleSignInAccount>) {
        try {
            val account : GoogleSignInAccount = task.getResult(ApiException::class.java)!!
            Toast.makeText(this,account.email,Toast.LENGTH_SHORT).show()
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            //intent.putExtra("account",account)
            Toast.makeText(this,"login success..",Toast.LENGTH_SHORT).show()
            FirebaseGoogleAuth(account)
            startActivity(intent)

        }
        catch (e : ApiException){
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show()
            FirebaseGoogleAuth(null)
        }
    }

    private fun FirebaseGoogleAuth(account: GoogleSignInAccount?) {
        val authcredential : AuthCredential = GoogleAuthProvider.getCredential(account!!.idToken,null)
        mAuth!!.signInWithCredential(authcredential).addOnCompleteListener(this) {task ->
            if(task.isSuccessful){
                Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
        if(currentUser != null)
            updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser) {
        Toast.makeText(this,"welcome"+currentUser.email,Toast.LENGTH_SHORT).show()
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth!!.currentUser
                    updateUI(user!!)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(applicationContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI()
                }

            }
    }

}