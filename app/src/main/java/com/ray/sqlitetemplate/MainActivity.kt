package com.ray.sqlitetemplate

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.SignInButton
import com.google.android.gms.auth.api.Auth
import android.support.v7.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignInClient


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val Tag: String = "MainActivity"
    //Use this key in your application by passing it with the key=API_KEY parameter.
    //API KEY = AIzaSyDEx8oMziKZfes-708JANP115BdkGYn09E

    //OAuth 2.0 client IDs
    //92259583712-2bmo4jl4f44r0bjd4gvhge6ik1p4sgbr.apps.googleusercontent.com

    private lateinit var mLogin_Id: EditText
    private lateinit var mLogin_Pw: EditText
    private lateinit var mAddButton: Button
    //private lateinit var mRemoveButton: Button
    private lateinit var dbController: DatabaseController
    private lateinit var mCheckListButton:Button

    private lateinit var signInButton:SignInButton
    companion object {
        val RC_SIGN_IN = 0;
        private lateinit var mGoogleSignInClient: GoogleSignInClient

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
        mLogin_Id = this.findViewById(R.id.login_id_edit_text)
        mLogin_Pw= findViewById(R.id.password_edit_text)
        mAddButton = findViewById(R.id.add_button)
        //mRemoveButton = findViewById(R.id.remove_button)
        mCheckListButton= findViewById(R.id.check_sql_button)

        dbController = DatabaseController(this)
        verifyStoragePermissions()
        assignListeners()
        googleSignIn()
    }

    private fun googleSignIn(){
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)

        // Set the dimensions of the sign-in button.
        signInButton = findViewById<SignInButton>(com.ray.sqlitetemplate.R.id.sign_in_button)
        signInButton.setSize(SignInButton.SIZE_STANDARD)

        signInButton.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)

    }

    private fun updateUI( account:GoogleSignInAccount?){
        //If GoogleSignIn.getLastSignedInAccount returns a GoogleSignInAccount object (rather than null),
        // the user has already signed in to your app with Google. Update your UI accordingly—that is, hide the sign-in button,
        // launch your main activity, or whatever is appropriate for your app.

        //If GoogleSignIn.getLastSignedInAccount returns null, the user has not yet signed in to your app with Google.
        // Update your UI to display the Google Sign-in button.

        when(account){
            //If account is null, it means the use has not yet signed in to the app with google login, then recall the sign-in activity again.
            null ->refreshListActivity()
            else ->{
                startSignInIntent()
                  /*  val startAfterSignIn_Activity = Intent(this, AfterLogIn_Activity::class.java)
                    startActivity(startAfterSignIn_Activity)
                 */
            }
        }
    }

    private fun startSignInIntent() {
        val signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
        val intent = signInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                // The signed in account is stored in the result.
                val signedInAccount = result.signInAccount
            } else {
                var message = result.status.statusMessage
                if (message == null || message.isEmpty()) {
                    message = getString(R.string.signin_other_error)
                }
                AlertDialog.Builder(this).setMessage(message)
                        .setNeutralButton(android.R.string.ok, null).show()
            }
        }
    }



    private fun assignListeners() {
        var loginStr:String = mLogin_Id.getText().toString()
        var pwStr:String = mLogin_Pw.getText().toString()
        val v = Log.v(Tag, "Current Input Id is $loginStr")

        mAddButton.setOnClickListener(this)
       // mRemoveButton.setOnClickListener(this)
        mCheckListButton.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        Log.i(Tag, "It is inside of onClick()")
        when (v.id) {
            R.id.add_button -> {
                Log.i(Tag, "It is inside of add_button")
                if(validateInputData(R.id.add_button)){
                    var loginData:LoginData = LoginData(mLogin_Id.text.toString(), mLogin_Pw.text.toString())
                    dbController.addData(loginData)
                }
                mLogin_Id.text.clear()
                mLogin_Pw.text.clear()

                    Toast.makeText(this, "Current ID is ${mLogin_Id.text} added", Toast.LENGTH_SHORT).show()
            }
            R.id.check_sql_button->{
                val listViewActivityIntent: Intent = Intent(this@MainActivity, ListViewActivity::class.java)
                listViewActivityIntent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                startActivity(listViewActivityIntent)
            }
        }
    }
    private fun validateInputData(button:Int): Boolean{
        when (button){
            R.id.add_button -> {
                if(mLogin_Id.text.toString().length <= 0){
                    Toast.makeText(this, "Please insert Login ID", Toast.LENGTH_SHORT).show()
                    return false
                }
                else if(mLogin_Pw.text.toString().length <= 0){
                    Toast.makeText(this, "Please insert Password", Toast.LENGTH_SHORT).show()
                    return false
                }
                return true
            }
        }
        return false
    }
        fun verifyStoragePermissions() {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    0
            )
        }
    }

    private val clickListener = View.OnClickListener {
    }

    fun refreshListActivity():Boolean{
        val refreshIntent:Intent = Intent(this, MainActivity::class.java)
        refreshIntent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY // Adds the FLAG_ACTIVITY_NO_HISTORY flag

        //Refreshing activity
        this.startActivity(refreshIntent)

        return true
    }

}
