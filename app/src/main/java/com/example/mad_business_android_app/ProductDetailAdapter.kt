package com.example.mad_business_android_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductDetailAdapter(private val productList: List<ProductModel>) :
    RecyclerView.Adapter<ProductDetailAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyler_item, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = productList[position]
        holder.productName.text = currentItem.productName
        holder.productPrice.text = currentItem.productPrice
        holder.productQuantity.text = currentItem.productQuantity
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.recTitle)
        val productPrice: TextView = itemView.findViewById(R.id.recPrice)
        val productQuantity: TextView = itemView.findViewById(R.id.recQuantity)
    }
}

