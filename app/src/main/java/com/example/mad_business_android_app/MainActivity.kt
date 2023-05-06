package com.example.mad_business_android_app


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mad_business_android_app.databinding.HometabsBinding
import com.google.firebase.FirebaseApp


class MainActivity : AppCompatActivity() {
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


       // FirebaseApp.initializeApp(this);


    }

}