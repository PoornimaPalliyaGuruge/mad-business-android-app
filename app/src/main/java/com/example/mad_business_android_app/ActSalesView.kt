package com.example.mad_business_android_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mad_business_android_app.databinding.ActivitySalesViewBinding
import com.google.firebase.database.*
import com.google.firebase.database.R

class ActSalesView:AppCompatActivity() {
    private lateinit var binding:ActivitySalesViewBinding
    private lateinit var dataList: ArrayList<SalesDataClass>
    private lateinit var adapter: MySalesAdapter
    var databaseReference: DatabaseReference? = null
    var eventListener: ValueEventListener? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalesViewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val gridLayoutManager = GridLayoutManager(this@ActSalesView,1)
        binding.recycleSalesView.layoutManager = gridLayoutManager

        val builder = AlertDialog.Builder(this@ActSalesView)
        builder.setCancelable(false)
        builder.setView(com.example.mad_business_android_app.R.layout.progress_layout)

        val dialog = builder.create()
        dialog.show()

        dataList = ArrayList()
        adapter = MySalesAdapter(this@ActSalesView, dataList)
        binding.recycleSalesView.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference("Sales Add")
        dialog.show()

        eventListener = databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (itemSnapshot in snapshot.children) {
                    val dataClass = itemSnapshot.getValue(SalesDataClass::class.java)
                    if (dataClass != null) {
                        dataList.add(dataClass)
                    }
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            override fun onCancelled(error: DatabaseError) {
                dialog.dismiss()
            }
        })


        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchList(newText)
                return true
            }

        })



    }
    fun searchList(text: String) {
        val searchList = ArrayList<SalesDataClass>()
        for (dataClass in dataList) {
            if (dataClass.cusID?.lowercase()?.contains(text.lowercase()) == true) {
                searchList.add(dataClass)
            }
        }
        adapter.searchDataList(searchList)
    }
}