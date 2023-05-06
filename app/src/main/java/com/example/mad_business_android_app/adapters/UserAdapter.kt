package com.example.mad_business_android_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mad_business_android_app.R
import com.example.mad_business_android_app.RetriveUserModel

class UserAdapter(private val userList: ArrayList<RetriveUserModel>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_user_card, parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.tvUserName.text = currentUser.name
    }

    override fun getItemCount(): Int{
        return userList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUserName : TextView = itemView.findViewById(R.id.userCardName)
    }
}