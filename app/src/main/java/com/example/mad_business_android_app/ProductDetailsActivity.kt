package com.example.mad_business_android_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class ProductDetailsActivity : AppCompatActivity(){

    private lateinit var tvProductId: TextView
    private lateinit var tvProductName: TextView
    private lateinit var tvProductPrice: TextView
    private lateinit var tvProductQuantity: TextView

    private lateinit var btnUpdateProduct: Button
    private lateinit var btnDeleteProduct: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_product)

        initView()
        setValuesToViews()

        btnUpdateProduct.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("productId").toString(),
                intent.getStringExtra("productName").toString(),
            )
        }

        btnDeleteProduct.setOnClickListener {
            deleteProduct(
                intent.getStringExtra("productId").toString()
            )
        }
    }

    private fun initView(){
        tvProductId = findViewById(R.id.productId) // initialize tvProductId
        tvProductName = findViewById(R.id.edtItemName)
        tvProductPrice = findViewById(R.id.edtItemPrice)
        tvProductQuantity = findViewById(R.id.edtQuantity)

        btnUpdateProduct = findViewById(R.id.btnUpdateProduct)
        btnDeleteProduct = findViewById(R.id.btnDeleteProduct)
    }

    private fun setValuesToViews(){

        tvProductId.text = intent.getStringExtra("productId")
        tvProductName.text = intent.getStringExtra("productName")
        tvProductPrice.text = intent.getStringExtra("productPrice")
        tvProductQuantity.text = intent.getStringExtra("productQuantity")

    }

    private fun openUpdateDialog(
        productId: String,
        productName: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_product_dialog, null)

        mDialog.setView(mDialogView)

        val etProductName = mDialogView.findViewById<EditText>(R.id.edtItemName)
        val etProductPrice = mDialogView.findViewById<EditText>(R.id.edtItemPrice)
        val etProductQuantity = mDialogView.findViewById<EditText>(R.id.edtQuantity)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateDataDialog)

        etProductName.setText(intent.getStringExtra("productName").toString())
        etProductPrice.setText(intent.getStringExtra("productPrice").toString())
        etProductQuantity.setText(intent.getStringExtra("productQuantity").toString())

        mDialog.setTitle("Updating $productName Details")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateProductData(
                productId,
                etProductName.text.toString(),
                etProductPrice.text.toString(),
                etProductQuantity.text.toString()
            )

            Toast.makeText(applicationContext, "Product data updated.", Toast.LENGTH_LONG).show()

            //setting updated data to TextView
            tvProductName.text = etProductName.text.toString()
            tvProductPrice.text = etProductPrice.text.toString()
            tvProductQuantity.text = etProductQuantity.text.toString()

            alertDialog.dismiss()

            val intent = Intent(this, FetchingAllProductActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun updateProductData(
        id: String,
         name: String,
        price: String,
        quantity: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Inventory").child(id)
        val productInfo = ProductModel(id, name, price, quantity)
//        dbRef.setValue(productInfo)

        dbRef.setValue(productInfo).addOnSuccessListener {
            val intent = Intent(this, FetchingAllProductActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    //Delete Products
    private fun deleteProduct(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Inventory").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Product deleted.", Toast.LENGTH_LONG).show()
            val intent = Intent(this, FetchingProductActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener {error ->
            Toast.makeText(this, "Deleting Error ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

}
