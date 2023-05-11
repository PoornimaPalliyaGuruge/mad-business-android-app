
package com.example.mad_business_android_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import androidx.appcompat.widget.SearchView
import java.util.*
import kotlin.collections.ArrayList

class FetchingAllProductActivity : AppCompatActivity() {
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var productList: ArrayList<ProductModel>
    private lateinit var filteredProductList: ArrayList<ProductModel>
    private lateinit var dbRef: DatabaseReference
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_products_view)

        productRecyclerView = findViewById(R.id.recyclerView)
        productRecyclerView.layoutManager = LinearLayoutManager(this)
        productRecyclerView.setHasFixedSize(true)

        productList = arrayListOf()
        filteredProductList = arrayListOf()

        searchView = findViewById<SearchView>(R.id.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    filteredProductList.clear()
                    val searchQuery = newText.toLowerCase(Locale.getDefault())
//                    val searchQuery = newText?.toLowerCase(Locale.getDefault()) ?: ""

                    if (searchQuery.isNotEmpty()) {
                        for (product in productList) {
                            if (product.productName?.toLowerCase(Locale.getDefault())?.contains(searchQuery) == true) {
                                filteredProductList.add(product)
                            }
                        }
                        val mAdapter = ProductDetailAdapter(filteredProductList)
                        productRecyclerView.adapter = mAdapter
                    } else {
                        val mAdapter = ProductDetailAdapter(productList)
                        productRecyclerView.adapter = mAdapter
                    }
                }
                return true
            }

        })

        getProductsData()

    }

    private fun getProductsData() {
        dbRef = FirebaseDatabase.getInstance().getReference("Inventory")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()
                if (snapshot.exists()){
                    for (productSnap in snapshot.children){
                        val productData = productSnap.getValue(ProductModel::class.java)
                        productData?.let {
                            productList.add(it)
                        }
                    }
                    val mAdapter = ProductDetailAdapter(productList)
                    productRecyclerView.adapter = mAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }

        })
    }
}
