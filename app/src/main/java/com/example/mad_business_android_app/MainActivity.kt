package com.example.mad_business_android_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mad_business_android_app.databinding.ActivityInventory01Binding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInventory01Binding
//    private lateinit var dataList: ArrayList<ProductModel>
//    private lateinit var adapter: MyAdapter
//    var databaseReference:DatabaseReference? = null
//    var eventListener:ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory01)
        binding = ActivityInventory01Binding.inflate(layoutInflater)
        setContentView(binding.root)

//        val gridLayoutManager = GridLayoutManager(this@MainActivity, 1)

        binding.btnAddItems.setOnClickListener{
            val intent = Intent(this@MainActivity, InsertionActivity::class.java)
            startActivity(intent)
        }

        binding.btnViewAll.setOnClickListener{
            val intent = Intent(this@MainActivity, InventoryDetails::class.java)
            startActivity(intent)
        }
    }
}
