package com.example.mad_business_android_app
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var userCreateBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_user_mgt_home)

        userCreateBtn = findViewById(R.id.userCreateBtn)

        userCreateBtn.setOnClickListener{
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)

        }



    }

}