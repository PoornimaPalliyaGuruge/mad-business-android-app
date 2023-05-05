package com.example.mad_business_android_app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mad_business_android_app.databinding.ActivitySalesAddBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.text.DateFormat
import java.util.*


class AddSalesActivity:AppCompatActivity() {
    private lateinit var binding: ActivitySalesAddBinding
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalesAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbRef = FirebaseDatabase.getInstance().getReference("Sales Add")

        val activityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data

            } else {
                Toast.makeText(this@AddSalesActivity, "Not completed", Toast.LENGTH_SHORT).show()
            }
        }

        binding.saveButton.setOnClickListener {
            saveData()
        }
    }
    private fun saveData(){
        val storageReference = FirebaseStorage.getInstance().reference.child("Task Images")


        val builder = AlertDialog.Builder(this@AddSalesActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        uploadData()


    }
    private fun uploadData() {

        val cusID = binding.addCusID.text.toString()
        val productName = binding.addProductName.text.toString()
        val quantity = binding.addQty.text.toString()
        val total = binding.addTotal.text.toString()

        if (cusID.isEmpty()) {
            binding.addCusID.error = "Please Enter Customer Name"
        }
        if (productName.isEmpty()) {
            binding.addProductName.error = "Please Enter Product Name"
        }
        if (quantity.isEmpty()) {
            binding.addQty.error = "Please Enter quantity"
        }
        if (total.isEmpty()) {
            binding.addTotal.error = "Please Enter total"
        }

        if (cusID.isNotEmpty() && productName.isNotEmpty() && quantity.isNotEmpty() && total.isNotEmpty()) {
            val salesID = dbRef.push().key!!
            val dataClass = SalesDataClass(salesID, cusID, productName, quantity, total)


            FirebaseDatabase.getInstance().getReference("Sales Add").child(salesID)
                .setValue(dataClass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@AddSalesActivity, "Saved", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        this@AddSalesActivity, e.message.toString(), Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

}
