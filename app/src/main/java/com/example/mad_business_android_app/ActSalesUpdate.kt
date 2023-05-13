//package com.example.mad_business_android_app
//
//import android.os.Bundle
//import android.os.PersistableBundle
//import android.widget.Toast
//import androidx.appcompat.app.AlertDialog
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.GridLayoutManager
//import com.example.mad_business_android_app.databinding.ActivitySalesUpdateBinding
//import com.example.mad_business_android_app.databinding.ActivitySalesViewBinding
//import com.google.firebase.database.*
//
//class ActSalesUpdate : AppCompatActivity() {
//
//    private  lateinit var binding: ActivitySalesUpdateBinding
//    private  lateinit var Sbinding: ActivitySalesViewBinding
//    private lateinit var databaseReference: DatabaseReference
//    private lateinit var dataList: ArrayList<SalesDataClass>
//    private lateinit var adapter: MySalesAdapter
//    var eventListener: ValueEventListener? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivitySalesUpdateBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val gridLayoutManager = GridLayoutManager(this@ActSalesUpdate,1)
//        Sbinding.recycleSalesView.layoutManager = gridLayoutManager
//
//        val builder = AlertDialog.Builder(this@ActSalesUpdate)
//        builder.setCancelable(false)
//        builder.setView(R.layout.progress_layout)
//
//        val dialog = builder.create()
//        dialog.show()
//
//        dataList = ArrayList()
//        adapter = MySalesAdapter(this@ActSalesUpdate, dataList)
//        Sbinding.recycleSalesView.adapter = adapter
//        databaseReference = FirebaseDatabase.getInstance().getReference("Sales Add")
//        dialog.show()
//
//        eventListener = databaseReference!!.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                dataList.clear()
//                for (itemSnapshot in snapshot.children) {
//                    val dataClass = itemSnapshot.getValue(SalesDataClass::class.java)
//                    if (dataClass != null) {
//                        dataList.add(dataClass)
//                    }
//                }
//                adapter.notifyDataSetChanged()
//                dialog.dismiss()
//            }
//            override fun onCancelled(error: DatabaseError) {
//                dialog.dismiss()
//            }
//        })
//
//
//        binding.updateButton.setOnClickListener{
//            val updateCusID = binding.updateCusID.text.toString()
//            val updateProductName = binding.updateProductName.text.toString()
//            val updateQty = binding.updateQty.text.toString()
//            val updateTotal = binding.updateTotal.text.toString()
//
//            updateSalesData(updateCusID,updateProductName,updateQty,updateTotal)
//
//        }
//
//    }
//
//    private fun updateSalesData(cusID:String, productName: String,quantity:String,total:String){
//        databaseReference = FirebaseDatabase.getInstance().getReference("Sales Add")
//        val sale = mapOf<String,String>("cusId" to cusID,"productName" to productName,"quantity" to quantity, "total" to total)
//        databaseReference.updateChildren(sale).addOnSuccessListener {
//            binding.updateCusID.text.clear()
//            binding.updateProductName.text.clear()
//            binding.updateQty.text.clear()
//            binding.updateTotal.text.clear()
//
//            Toast.makeText(this,"Updated",Toast.LENGTH_SHORT).show()
//        }.addOnFailureListener{
//            Toast.makeText(this,"Unable to Updated",Toast.LENGTH_SHORT).show()
//        }
//    }
//}