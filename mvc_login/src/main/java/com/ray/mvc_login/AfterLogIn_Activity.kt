package com.ray.sqlitetemplate

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task


class AfterLogIn_Activity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_log_in_)
        findViewById<Button>(R.id.sign_out_button).setOnClickListener(this)

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.sign_out_button -> {
                signOut()
            }
        }
    }

    //google sign out user
    private fun signOut(){
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, object: OnCompleteListener<Void>{
                    override fun onComplete(p0: Task<Void>) {
                    }
                })
    }

    //Disconnect Accounts
    //To provide users that signed in with Google the ability to disconnect their Google account from your app
    private fun revokeAccess(){
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, object: OnCompleteListener<Void>{
                    override fun onComplete(p0: Task<Void>) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
    }
}
