package com.example.mad_business_android_app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.mad_business_android_app.databinding.ActivitySalesAddBinding
import com.google.firebase.database.FirebaseDatabase
import java.text.DateFormat
import java.util.*


class AddSalesActivity:AppCompatActivity() {
    private lateinit var binding: ActivitySalesAddBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalesAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val activityResultLauncher = registerForActivityResult<Intent , ActivityResult>(
            ActivityResultContracts.StartActivityForResult()){ result ->
                if (result.resultCode == RESULT_OK){
                   val data = result.data


                }else{
                    Toast.makeText(this,"success.",Toast.LENGTH_SHORT).show()
                }
            }
       uploadData()

    }

    private fun uploadData() {
        val cusID = binding.addCusID.text.toString()
        val productName = binding.addProductName.text.toString()
        val qty = binding.addQty.text.toString()
        val total = binding.addTotal.text.toString()


        val dataClass = SalesDataClass(cusID,productName, qty,total)
        val currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)

        FirebaseDatabase.getInstance().getReference("Add Sales").child(currentDate).setValue(dataClass).addOnCompleteListener{task->
            if (task.isSuccessful){
                Toast.makeText(this@AddSalesActivity,"Saved.",Toast.LENGTH_SHORT).show()
                finish()
            }
        }.addOnFailureListener{ e->
            Toast.makeText(this@AddSalesActivity,e.message.toString(),Toast.LENGTH_SHORT).show()
        }



    }
}
