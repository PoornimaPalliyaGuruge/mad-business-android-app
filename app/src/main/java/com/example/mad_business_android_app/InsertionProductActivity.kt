//package com.example.mad_business_android_app
//
//import android.app.AlertDialog
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//
//class InsertionProductActivity : AppCompatActivity() {
//
//    private lateinit var etproductName: EditText
//    private lateinit var etproductPrice: EditText
//    private lateinit var etproductQuantity: EditText
//    private lateinit var btnAddItem: Button
//
//    private lateinit var dbRef: DatabaseReference
//    private lateinit var dialog: AlertDialog
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_addproducts)
//
//        etproductName = findViewById(R.id.edtItemName)
//        etproductPrice = findViewById(R.id.edtItemPrice)
//        etproductQuantity = findViewById(R.id.edtItemQuantity)
//        btnAddItem = findViewById(R.id.btnAdditem)
//
//        dbRef = FirebaseDatabase.getInstance().getReference("Inventory")
//
//        dialog = createProgressDialog()
//
//        btnAddItem.setOnClickListener {
//            dialog.show()
//            saveProductData()
//        }
//    }
//
//    private fun createProgressDialog(): AlertDialog {
//        val builder = AlertDialog.Builder(this@InsertionProductActivity)
//        builder.setCancelable(false)
//        builder.setView(R.layout.progess_layout)
//        return builder.create()
//    }
//
//    private fun saveProductData() {
//        //getting input values
//        val productName = etproductName.text.toString()
//        val productPrice = etproductPrice.text.toString()
//        val productQuantity = etproductQuantity.text.toString()
//
//        if (productName.isEmpty()) {
//            etproductName.error = "Please enter the product name"
//            dialog.dismiss()
//            return
//        }
//        if (productPrice.isEmpty()) {
//            etproductPrice.error = "Please enter the product price"
//            dialog.dismiss()
//            return
//        }
//        if (productQuantity.isEmpty()) {
//            etproductQuantity.error = "Please enter the product quantity"
//            dialog.dismiss()
//            return
//        }
//
//        val productId = dbRef.push().key!!
//
//        val product = ProductModel(productId, productName, productPrice, productQuantity)
//
//        dbRef.child(productId).setValue(product)
//            .addOnCompleteListener {
//                Toast.makeText(this, "Product added successfully", Toast.LENGTH_LONG).show()
//                etproductName.text.clear()
//                etproductPrice.text.clear()
//                etproductQuantity.text.clear()
//                dialog.dismiss()
//            }.addOnFailureListener { err ->
//                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
//                dialog.dismiss()
//            }
//    }
//}
//
//


package com.example.mad_business_android_app

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionProductActivity : AppCompatActivity() {

    private lateinit var etproductName: EditText
    private lateinit var etproductPrice: EditText
    private lateinit var etproductQuantity: EditText
    private lateinit var btnAddItem: Button

    private lateinit var dbRef: DatabaseReference
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addproducts)

        etproductName = findViewById(R.id.edtItemName)
        etproductPrice = findViewById(R.id.edtItemPrice)
        etproductQuantity = findViewById(R.id.edtItemQuantity)
        btnAddItem = findViewById(R.id.btnAdditem)

        dbRef = FirebaseDatabase.getInstance().getReference("Inventory")

        dialog = createProgressDialog()

        btnAddItem.setOnClickListener {
            dialog.show()
            saveProductData()
        }
    }

    private fun createProgressDialog(): AlertDialog {
        val builder = AlertDialog.Builder(this@InsertionProductActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progess_layout)
        return builder.create()
    }

    private fun saveProductData() {
        //getting input values
        val productName = etproductName.text.toString()
        val productPrice = etproductPrice.text.toString()
        val productQuantity = etproductQuantity.text.toString()

        if (productName.isEmpty()) {
            etproductName.error = "Please enter the product name"
            dialog.dismiss()
            return
        }
        if (productPrice.isEmpty()) {
            etproductPrice.error = "Please enter the product price"
            dialog.dismiss()
            return
        }
        if (productQuantity.isEmpty()) {
            etproductQuantity.error = "Please enter the product quantity"
            dialog.dismiss()
            return
        }

        val productId = dbRef.push().key!!

        val product = ProductModel(productId, productName, productPrice, productQuantity)

        dbRef.child(productId).setValue(product)
            .addOnCompleteListener {
                Toast.makeText(this, "Product added successfully", Toast.LENGTH_LONG).show()
                etproductName.text.clear()
                etproductPrice.text.clear()
                etproductQuantity.text.clear()
                dialog.dismiss()
                val intent = Intent(this, FetchingAllProductActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
    }
}




