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
    }

}