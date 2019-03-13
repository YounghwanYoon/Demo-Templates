package com.ray.sqlite

import android.nfc.Tag
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity: AppCompatActivity(), View.OnClickListener {

    private val Tag:String = "MainActivity"
    private lateinit var mLogin_Id: EditText
    private lateinit var mLogin_Pw: EditText

    private lateinit var mAddButton: Button
    private lateinit var mRemoveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity);
        mLogin_Id = findViewById(R.id.login_id_editText)
        mAddButton=findViewById(R.id.add_button)

        assignListeners()
    }

    private fun assignListeners(){
        var loginStr: String;
        loginStr = mLogin_Id.selectAll().toString();
        val v = Log.v(Tag, "Current Input Id is $loginStr")

        mAddButton.setOnClickListener(this)

    }

    override fun onClick(v:View){
        when(v.id){
            R.id.add_button -> {
                Toast.makeText(this,"Current ID is ${mLogin_Id.text.toString()}" ,Toast.LENGTH_SHORT);
            }
            R.id.remove_button ->{
                //TODO
            }

        }
    }

    private val clickListener = View.OnClickListener {


    }

}
