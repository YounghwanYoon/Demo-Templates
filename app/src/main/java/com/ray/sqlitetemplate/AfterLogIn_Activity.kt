package com.ray.sqlitetemplate

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task


class AfterLogIn_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_log_in_)
        findViewById<Button>(R.id.sign_out_button).setOnClickListener(View.OnClickListener {
            signOut()
        })
    }

    fun signOut(){
        /*mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, object : OnCompleteListener<Void>() {
                    override fun onComplete(task: Task<Void>) {
                        // ...
                    }
                })
*/
    }

}
