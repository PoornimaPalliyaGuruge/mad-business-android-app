package com.example.mad_business_android_app


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mad_business_android_app.activity.ExpenseHome
import com.example.mad_business_android_app.databinding.HometabsBinding
import com.google.firebase.FirebaseApp


class MainActivity : AppCompatActivity() {
    private lateinit var binding: HometabsBinding
    private lateinit var expenseBtn: Button

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

        expenseBtn= findViewById(R.id.btn_expense)
         expenseBtn.setOnClickListener{

            val intent = Intent(this, ExpenseHome::class.java)
            startActivity(intent)

        }





       // FirebaseApp.initializeApp(this);

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
