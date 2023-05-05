package com.example.mad_business_android_app

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputBinding
import androidx.appcompat.app.AppCompatActivity
import com.example.mad_business_android_app.databinding.ActivitySalesBinding

class ActSalesHome : AppCompatActivity() {

    private lateinit var binding: ActivitySalesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalesBinding.inflate(layoutInflater)
        setContentView(binding.root)


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