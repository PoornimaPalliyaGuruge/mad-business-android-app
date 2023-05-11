package com.example.mad_business_android_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//import com.example.mad_business_android_app.R
//import com.example.mad_business_android_app.ProductModel

class ProductAdapter(private val productList: ArrayList<ProductModel>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>(){

    private lateinit var mListner: onItemClickListner

    interface onItemClickListner{
        fun onItemClick(position: Int)
    }

    fun setonItemClickListner(clickListner: onItemClickListner){
        mListner = clickListner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val productView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_edit_products, parent, false)
        return ViewHolder(productView, mListner)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        val currentProduct = productList[position]
        holder.tvproductName.text = currentProduct.productName
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ViewHolder(productView : View, clickListner: onItemClickListner) : RecyclerView.ViewHolder(productView){
        val tvproductName : TextView = productView.findViewById(R.id.recTitle)

        init {
            productView.setOnClickListener{
                clickListner.onItemClick(adapterPosition)
            }
        }
    }
}
