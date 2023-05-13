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

class ActSalesDetails : AppCompatActivity(){

    private lateinit var tvSldSalesID :TextView
    private lateinit var tvSldCusId :TextView
    private lateinit var tvSldPName :TextView
    private lateinit var tvSldQty :TextView
    private lateinit var tvSldTotal :TextView
    private lateinit var btnSlUpdate :Button
    private lateinit var btnSlDelete :Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales_details)



        initView()
        setValuesToView()

        btnSlUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("salesID").toString(),
                intent.getStringExtra("cusID").toString()

            )
        }

        btnSlDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("salesID").toString() ,

            )
        }
    }

    private fun deleteRecord(
        id:String
    ){
        val  databaseReference = FirebaseDatabase.getInstance().getReference("Sales Add").child(id)
        val mTask = databaseReference.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Sales Data Deleted",Toast.LENGTH_LONG).show()
            val intent = Intent(this,FetchingSalesData::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{error ->
            Toast.makeText(this,"Deleting error ${error.message}",Toast.LENGTH_LONG).show()
        }
    }

    private fun initView() {
        tvSldSalesID = findViewById(R.id.tvSldSalesID)
        tvSldCusId = findViewById(R.id.tvSldCusId)
        tvSldPName = findViewById(R.id.tvSldPName)
        tvSldQty = findViewById(R.id.tvSldQty)
        tvSldTotal = findViewById(R.id.tvSldTotal)

        btnSlUpdate = findViewById(R.id.btnSlUpdate)
        btnSlDelete = findViewById(R.id.btnSlDelete)
    }


    private fun setValuesToView(){
        tvSldSalesID.text = intent.getStringExtra("salesID")
        tvSldCusId.text = intent.getStringExtra("cusID")
        tvSldPName.text = intent.getStringExtra("productName")
        tvSldQty.text = intent.getStringExtra("quantity")
        tvSldTotal.text = intent.getStringExtra("total")

    }

    private fun openUpdateDialog(
        salesID: String,
        cusID: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.sales_update_dialog,null)

        mDialog.setView(mDialogView)

        val etSldCusId = mDialogView.findViewById<EditText>(R.id.etSldCusId)
        val etSldPName = mDialogView.findViewById<EditText>(R.id.etSldPName)
        val etSldQty = mDialogView.findViewById<EditText>(R.id.etSldQty)
        val etSldTotal = mDialogView.findViewById<EditText>(R.id.etSldTotal)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etSldCusId.setText(intent.getStringExtra("cusID").toString())
        etSldPName.setText(intent.getStringExtra("productName").toString())
        etSldQty.setText(intent.getStringExtra("quantity").toString())
        etSldTotal.setText(intent.getStringExtra("total").toString())

        mDialog.setTitle("Updating $cusID Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{
            updateSalesData(
                salesID,
                etSldCusId.text.toString(),
                etSldPName.text.toString(),
                etSldQty.text.toString(),
                etSldTotal.text.toString()
            )

            Toast.makeText(applicationContext,"Sales data Updated", Toast.LENGTH_SHORT).show()

            //set updated data in to textview
            tvSldCusId.text =  etSldCusId.text.toString()
            tvSldPName.text = etSldPName.text.toString()
            tvSldQty.text =  etSldQty.text.toString()
            tvSldTotal.text = etSldTotal.text.toString()

            alertDialog.dismiss()
        }

    }

    private fun updateSalesData(
        salesID:String,
        ID: String,
        productName: String,
        quantity: String,
        total: String
    ) {

        val  databaseReference = FirebaseDatabase.getInstance().getReference("Sales Add").child(salesID)
        val salesInfo = SalesDataClass(salesID,ID,productName,quantity,total)
        databaseReference.setValue(salesInfo)
    }
}
