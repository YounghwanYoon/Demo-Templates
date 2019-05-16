package com.ray.sqlitetemplate.view.adapter

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


class UserRecycleAdapter(private val userList:ArrayList<UserData>, private val mContext: Context):RecyclerView.Adapter<UserRecycleAdapter.ViewHolder>(){

    companion object {
        private var TAG="UserRecycleAdapter"
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_user_data, parent, false)
        return ViewHolder(view, mContext)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(userList[position])

    }

    //it hold each individual data such as image or text
    class ViewHolder(itemView:View, context:Context):RecyclerView.ViewHolder(itemView) {
        private var image: CircleImageView = itemView.findViewById<CircleImageView>(com.ray.sqlitetemplate.R.id.image)
            get() = field
            set(value) {
                field = value
            }
        var userName: TextView = itemView.findViewById<TextView>(R.id.user_name_text)
        var userEmail:TextView = itemView.findViewById<TextView>(R.id.user_email_text)
        var parentLayout:LinearLayout = itemView.findViewById<LinearLayout>(R.id.each_data)
        private val mContext = context

        fun bindItems(user: UserData){
            Log.d(TAG, "bindItems is ${mContext}")

            Glide.with(mContext)
                    .asBitmap()
                    .load(user.mImage)
                    .into(this.image)

            this.userName.text = user.mLoginID
            this.userEmail.text = user.mEmail

            this.parentLayout.setOnClickListener(object:View.OnClickListener{
                override fun onClick(p0: View?) {
                    Toast.makeText(mContext, "Clicked: ${user.mLoginID}", Toast.LENGTH_SHORT)
                }
            })
        }
    }
}