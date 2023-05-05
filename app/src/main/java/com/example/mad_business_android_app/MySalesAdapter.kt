package com.example.mad_business_android_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MySalesAdapter(private val context: Context, private var dataList: List<SalesDataClass> ):
    RecyclerView.Adapter<MyViewHolder>() {

    private var onDeleteClickListener: OnDeleteClickListener? = null

    fun setOnDeleteClickListener(listener: OnDeleteClickListener) {
        onDeleteClickListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.sales_recycle_item,parent,false)
        return MyViewHolder(view).apply {
            btnDelete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val dataClass = dataList[position]
                    onDeleteClickListener?.onDeleteClick(dataClass)
                }
            }
        }
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.cusID.text = dataList[position].cusID
        holder.productName.text = dataList[position].productName
        holder.quantity.text = dataList[position].quantity
        holder.total.text = dataList[position].total
        holder.recCard.setOnClickListener {

        }
    }
    override fun getItemCount(): Int {
        return dataList.size
    }
    fun searchDataList(searchList: List<SalesDataClass>) {
        dataList = searchList
        notifyDataSetChanged()
    }
    interface OnDeleteClickListener {
        fun onDeleteClick(dataClass: SalesDataClass)
    }



}

class MyViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
    var cusID : TextView
    var productName : TextView
    var quantity : TextView
    var total : TextView
    var recCard : CardView
    var btnUpdate: Button
    var btnDelete: Button


    init {
        cusID = itemView.findViewById(R.id.recSCusID)
        productName = itemView.findViewById(R.id.recSProduct)
        quantity = itemView.findViewById(R.id.recSQty)
        total = itemView.findViewById(R.id.resSTotal)
        recCard = itemView.findViewById(R.id.salesRecCard)
        btnUpdate = itemView.findViewById(R.id.btnSalesUpdate)
        btnDelete = itemView.findViewById(R.id.btnSalesDelete)

    }

}