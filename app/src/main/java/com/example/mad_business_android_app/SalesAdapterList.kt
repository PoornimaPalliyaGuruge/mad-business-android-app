package com.example.mad_business_android_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class SalesAdapterList(private val salesList:ArrayList<SalesDataClass>) : RecyclerView.Adapter<SalesAdapterList.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.sales_recycle_details,parent,false)
        return ViewHolder(itemView,mListener)


    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val currentSale = salesList[position]
        holder.recSCusID.text = currentSale.cusID
//        holder.recSQty.text = currentSale.quantity
//        holder.recSProduct.text = currentSale.productName
//        holder.resSTotal.text = currentSale.total


    }

    override fun getItemCount(): Int {
        return salesList.size
    }

    class ViewHolder(itemView : View,clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val recSCusID : TextView = itemView.findViewById(R.id.recSCusID)
//        val recSQty : TextView = itemView.findViewById(R.id.recSQty)
//        val recSProduct : TextView = itemView.findViewById(R.id.recSProduct)
//        val resSTotal : TextView = itemView.findViewById(R.id.resSTotal)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }


}

