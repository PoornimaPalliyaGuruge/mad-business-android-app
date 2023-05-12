package com.example.mad_business_android_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mad_business_android_app.adapters.AllProfAdapter
import com.example.mad_business_android_app.databinding.ActivityViewAllBinding
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class AllProfilesActivity:AppCompatActivity() {

    private lateinit var dataList: ArrayList<UserModel>
    private lateinit var adapter: AllProfAdapter
    private lateinit var binding: ActivityViewAllBinding
    var databaseReference: DatabaseReference? = null
    var eventListener : ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAllBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gridLayoutManager = GridLayoutManager(this@AllProfilesActivity, 1)
        binding.recyclerView.layoutManager = gridLayoutManager

//        val builder = AlertDialog.Builder(this@AllProfilesActivity)
//        builder.setCancelable(false)
//        builder.setView(R.layout.activity_view_all)
//        val dialog = builder.create()
//        dialog.show()

        dataList = ArrayList()
        adapter = AllProfAdapter(this@AllProfilesActivity, dataList)
        binding.recyclerView.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
//        dialog.show()

        eventListener = databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (itemSnapshot in snapshot.children) {
                    val dataClass = itemSnapshot.getValue(UserModel::class.java)
                    if (dataClass != null) {
                        dataList.add(dataClass)
                    }
                }
                adapter.notifyDataSetChanged()
//                dialog.dismiss()
            }
            override fun onCancelled(error: DatabaseError) {
//                dialog.dismiss()
            }
        })

        binding.fab.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@AllProfilesActivity, FetchingActivity::class.java)
            startActivity(intent)
        })

        binding.userProfSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                searchList(newText)
                return true
            }
        })

    }
    fun searchList(text: String) {
        val searchList = java.util.ArrayList<UserModel>()
        for (dataClass in dataList) {
            if (dataClass.name?.lowercase()
                    ?.contains(text.lowercase(Locale.getDefault())) == true
            ) {
                searchList.add(dataClass)
            }
        }
        adapter.searchDataList(searchList)
    }
}