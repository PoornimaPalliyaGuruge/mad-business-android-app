package com.example.mad_business_android_app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mad_business_android_app.R
import com.example.mad_business_android_app.UserModel

class AllProfAdapter(private val context: Context, private var dataList: List<UserModel>) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_user_card, parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.recName.text = dataList[position].name

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    fun searchDataList(searchList: List<UserModel>) {
        dataList = searchList
        notifyDataSetChanged()
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var recName: TextView

    init {
        recName = itemView.findViewById(R.id.userCardName)
    }
}