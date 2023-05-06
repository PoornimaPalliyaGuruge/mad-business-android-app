package com.example.mad_business_android_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


class FetchingSalesData : AppCompatActivity() {

    private lateinit var salesRecycleView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var salesList: ArrayList<SalesDataClass>
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updatedelete_list)

        salesRecycleView = findViewById(R.id.recycleList)
        salesRecycleView.layoutManager = LinearLayoutManager(this)
        salesRecycleView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        salesList = arrayListOf<SalesDataClass>()

        getSalesData()
    }

    private fun getSalesData(){
        salesRecycleView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        databaseReference = FirebaseDatabase.getInstance().getReference("Sales Add")

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               salesList.clear()
                if(snapshot.exists()){
                    for(salesSnap in snapshot.children){
                        val salesData = salesSnap.getValue(SalesDataClass::class.java)
                        salesList.add(salesData!!)

                    }
                    val mAdapter = SalesAdapterList(salesList)
                    salesRecycleView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : SalesAdapterList.onItemClickListener{
                        override fun onItemClick(position: Int) {
                           val intent = Intent(this@FetchingSalesData, ActSalesDetails::class.java)

                            //put details
                            intent.putExtra("salesID",salesList[position].salesID)
                            intent.putExtra("cusID",salesList[position].cusID)
                            intent.putExtra("productName",salesList[position].productName)
                            intent.putExtra("quantity",salesList[position].quantity)
                            intent.putExtra("total",salesList[position].total)
                            startActivity(intent)
                        }

                    })

                    salesRecycleView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}