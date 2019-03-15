package com.ray.sqlitetemplate

import android.os.Bundle
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
    private lateinit var mRemoveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
        mLogin_Id = findViewById(R.id.login_id_edit_text)
        mLogin_Pw= findViewById(R.id.password_edit_text)
        mAddButton = findViewById(R.id.add_button)
        mRemoveButton = findViewById(R.id.remove_button)
        assignListeners()
    }

    private fun assignListeners() {
        var loginStr:String = mLogin_Id.getText().toString()
        var pwStr:String = mLogin_Pw.getText().toString()
        val v = Log.v(Tag, "Current Input Id is $loginStr")

        mAddButton.setOnClickListener(this)
        mRemoveButton.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        Log.i(Tag, "It is inside of onClick()")
        when (v.id) {
            R.id.add_button -> {
                Log.i(Tag, "It is inside of add_button")
                Toast.makeText(this, "Current ID is ${mLogin_Id.text} added", Toast.LENGTH_SHORT).show()
            }
            R.id.remove_button -> {
             Toast.makeText(this, "Current ID is ${mLogin_Id.text} removed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val clickListener = View.OnClickListener {


    }

}
