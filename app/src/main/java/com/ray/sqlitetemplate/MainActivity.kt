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

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val Tag: String = "MainActivity"
    private lateinit var mLogin_Id: EditText
    private lateinit var mLogin_Pw: EditText
    private lateinit var mAddButton: Button
    //private lateinit var mRemoveButton: Button
    private lateinit var dbController: DatabaseController
    private lateinit var mCheckListButton:Button

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
}
