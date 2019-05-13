package com.ray.sqlitetemplate.view.view_adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.ray.sqlitetemplate.R
import de.hdodenhof.circleimageview.CircleImageView
import com.ray.sqlitetemplate.repository.model.UserData


class User_recycle_adapter(private val userList:ArrayList<UserData>, private val mContext: Context):RecyclerView.Adapter<User_recycle_adapter.ViewHolder>(){

    companion object {
        private var TAG="User_recycle_adapter";
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_user_data_view, parent, false)
        return ViewHolder(view, parent.context);
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(userList[position])
        holder.parentLayout.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                Toast.makeText(mContext, "Clicked: ${userList[position].mLoginID}", Toast.LENGTH_SHORT)
            }
        })
    }

    //it hold each individual data such as image or text
    class ViewHolder(itemView:View, context:Context):RecyclerView.ViewHolder(itemView){
        lateinit var parentLayout:LinearLayout
        lateinit var image: CircleImageView
        lateinit var userName: TextView
        lateinit var userEmail:TextView
        private val mContext = context

        fun bindItems(user:UserData){
            Log.d(TAG, "userdata is ${user.toString()}")
            this.image = CircleImageView(mContext).findViewById(R.id.image)

            //variable instantiation
            //image=itemView!!.findViewById<CircleImageView>(com.ray.sqlitetemplate.R.id.image)
            this.userName=itemView!!.findViewById<TextView>(R.id.user_name_text)
            this.userEmail=itemView!!.findViewById(R.id.user_email_text)
            this.parentLayout = itemView!!.findViewById(R.id.each_data)

            // data binding

            Glide.with(mContext)
                    .asBitmap()
                    .load(user.mImage)
                    .into(this.image)
            userName.setText(user.mLoginID);
            userEmail.setText(user.mEmail)


        }
    }
}