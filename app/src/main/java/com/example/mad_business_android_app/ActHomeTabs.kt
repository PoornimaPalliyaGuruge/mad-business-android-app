package com.example.mad_business_android_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.mad_business_android_app.databinding.ActivitySalesBinding
import com.example.mad_business_android_app.databinding.HometabsBinding

class ActHomeTabs: AppCompatActivity() {
    private lateinit var binding: HometabsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HometabsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSaleshome.setOnClickListener {
            Toast.makeText(this, "Activity started!"+ intent, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ActSalesHome::class.java)
            startActivity(intent)
            Toast.makeText(this, "Activity started!"+ intent, Toast.LENGTH_SHORT).show()
        }

    }
}