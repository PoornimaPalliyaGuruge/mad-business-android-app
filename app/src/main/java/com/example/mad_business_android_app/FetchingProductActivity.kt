package com.example.mad_business_android_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class FetchingProductActivity : AppCompatActivity(){

    private lateinit var productRecyclerView: RecyclerView
    private lateinit var productList: ArrayList<ProductModel>
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        productRecyclerView = findViewById(R.id.recyclerView)
        productRecyclerView.layoutManager = LinearLayoutManager(this)
        productRecyclerView.setHasFixedSize(true)

        productList = arrayListOf<ProductModel>()

        getProductsData()

    }

    private fun getProductsData(){
        dbRef = FirebaseDatabase.getInstance().getReference("Inventory")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()
                if (snapshot.exists()){
                    for (productSnap in snapshot.children){
                        val productData = productSnap.getValue(ProductModel::class.java)
                        productList.add(productData!!)
                    }
                    val mAdapter = ProductAdapter(productList)
                    productRecyclerView.adapter = mAdapter

                    mAdapter.setonItemClickListner(object  : ProductAdapter.onItemClickListner{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingProductActivity, ProductDetailsActivity::class.java)
                            //put extra data

                            intent.putExtra("productId", productList[position].productId)
                            intent.putExtra("productName", productList[position].productName)
                            intent.putExtra("productPrice", productList[position].productPrice)
                            intent.putExtra("productQuantity", productList[position].productQuantity)
                            startActivity(intent)
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}

