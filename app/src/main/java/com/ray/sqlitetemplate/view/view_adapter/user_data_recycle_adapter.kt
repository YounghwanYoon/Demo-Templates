package com.ray.sqlitetemplate.view.view_adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ray.sqlitetemplate.R
import de.hdodenhof.circleimageview.CircleImageView
import com.ray.sqlitetemplate.repository.model.LoginData


public class user_data_recycle_adapter(val userList:List<LoginData>):RecyclerView.Adapter<user_data_recycle_adapter.ViewHolder>(){


    companion object {
        private var TAG="user_data_recycle_adapter";
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_user_data_view, parent, false)
        return ViewHolder(view);
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(userList[position])
        
    }

    //it hold each individual data such as image or text
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        lateinit var parentLayout:LinearLayout
        lateinit var image: CircleImageView
        lateinit var userName: TextView
        lateinit var userEmail:TextView

        fun bindItems(user:LoginData){
            //variable instantiation
            image=itemView.findViewById<CircleImageView>(R.id.image)
            userName=itemView.findViewById<TextView>(R.id.user_name_text)
            userEmail=itemView.findViewById(R.id.user_email_text);

            // data binding


        }
    }
}