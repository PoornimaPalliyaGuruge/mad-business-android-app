package com.example.mad_business_android_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class InventoryHome : AppCompatActivity(){
    private lateinit var btnInsertProduct: Button
    private lateinit var btnFetchProduct: Button
    private lateinit var btneditProduct: Button
    private lateinit var tvTotalproducts: TextView
    private lateinit var tvTotalStock: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory01)

        tvTotalproducts = findViewById(R.id.tvTotalproducts)
        tvTotalStock = findViewById(R.id.tvTotalStock)

        getProductCountFromDatabase()
        getTotalStockValue()

        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()

        btnInsertProduct = findViewById(R.id.btnAddItems)
        btnInsertProduct.setOnClickListener {
            val intent = Intent(this,InsertionProductActivity::class.java)
            startActivity(intent)
        }

        btnFetchProduct = findViewById(R.id.btnViewAll)
        btnFetchProduct.setOnClickListener {
            val intent = Intent(this,FetchingAllProductActivity::class.java)
            startActivity(intent)
        }

        btneditProduct = findViewById(R.id.btneditProduct)
        btneditProduct.setOnClickListener {
            val intent = Intent(this, FetchingProductActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getProductCountFromDatabase() {
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Inventory")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val count: Long = snapshot.childrenCount
                tvTotalproducts.text = "$count" // set the count value in the TextView
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors
                Log.e("InventoryHome", "Database operation cancelled: ${error.message}")
                Toast.makeText(this@InventoryHome, "Database operation cancelled", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getTotalStockValue() {
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Inventory")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var totalValue = 0.0
                for (productSnapshot in snapshot.children) {
                    val product = productSnapshot.getValue(ProductModel::class.java)
                    if (product != null) {
                        val priceString = product.productPrice?.replace("Rs.", "")?.trim() ?: "0.0"
                        val price = priceString.toDoubleOrNull() ?: 0.0
                        val quantityString = product.productQuantity?.trim() ?: "0.0"
                        val quantity = quantityString.toDoubleOrNull() ?: 0.0
                        if (price != null && quantity != null) {
                            totalValue += price * quantity
                        }
                    }
                }
                // set the total value in the TextView
                tvTotalStock.text = "$totalValue"
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors
                Log.e("InventoryHome", "Database operation cancelled: ${error.message}")
                Toast.makeText(this@InventoryHome, "Database operation cancelled", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
