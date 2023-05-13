package com.example.mad_business_android_app

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputBinding
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mad_business_android_app.databinding.ActivitySalesBinding
import com.google.firebase.database.*

class ActSalesHome : AppCompatActivity() {

    private lateinit var binding: ActivitySalesBinding
    private lateinit var tv_sh_total:TextView
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tv_sh_total = findViewById(R.id.tv_sh_total)
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Sales Add")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var sum = 0
                dataSnapshot.children.forEach { ds ->
                    val map = ds.value as Map<String, Any>
                    val total = map["total"]
                    val tValue = total.toString().toInt()
                    sum += tValue
                }
                tv_sh_total.text = sum.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // handle error here
            }
        })

        binding.salesAddFab.setOnClickListener {
            val intent = Intent(this,AddSalesActivity::class.java)
            startActivity(intent)
        }

        binding.salesViewFab.setOnClickListener {
            val intent = Intent(this,ActSalesView::class.java)
            startActivity(intent)
        }
        binding.salesUpDelFab.setOnClickListener {
            val intent = Intent(this,FetchingSalesData::class.java)
            startActivity(intent)
        }
    }
}
