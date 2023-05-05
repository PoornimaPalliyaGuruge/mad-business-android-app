package com.example.mad_business_android_app

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mad_business_android_app.Adapters.UserAdapter
import com.google.firebase.database.*

class FetchingActivity : AppCompatActivity() {

    private lateinit var userRecyclerView : RecyclerView
    private lateinit var tvLoadingData : TextView
    private lateinit var userList : ArrayList<UserModel>
    private lateinit var dbRef :  DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_mgt_home)

        userRecyclerView = findViewById(R.id.usersRecyclerView)
        userRecyclerView.layoutManager= LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        userList = arrayListOf<UserModel>()
        getUsersData()
    }
     private fun getUsersData() {
        userRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        dbRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                if(snapshot.exists()){
                    for(userSnap in snapshot.children){
                        val userData = userSnap.getValue(UserModel::class.java)
                        userList.add(userData!!)
                    }
                    val mAdapter = UserAdapter(userList)
                    userRecyclerView.adapter = mAdapter
                    userRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}