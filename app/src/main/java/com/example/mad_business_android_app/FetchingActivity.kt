package com.example.mad_business_android_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mad_business_android_app.adapters.UserAdapter
import com.example.mad_business_android_app.databinding.ActivityUserMgtHomeBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import kotlin.collections.ArrayList

class FetchingActivity : AppCompatActivity() {
    private lateinit var userRecyclerView : RecyclerView
//    private lateinit var tvLoadingData : TextView
    private lateinit var userList : ArrayList<UserModel>
    private lateinit var dbRef :  DatabaseReference
    private lateinit var userCreateBtn : Button
    private lateinit var userBackBtn: FloatingActionButton
    private lateinit var allUserBtn : Button

//    private lateinit var binding: ActivityUserMgtHomeBinding
//    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//
//        binding = ActivityUserMgtHomeBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        setContentView(R.layout.activity_user_mgt_home)
        userRecyclerView = findViewById(R.id.usersRecyclerView)
        userRecyclerView.layoutManager= LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
//        tvLoadingData = findViewById(R.id.tvLoadingData)


        userList = arrayListOf<UserModel>()
        getUsersData()


        userCreateBtn = findViewById(R.id.userCreateBtn)

        userCreateBtn.setOnClickListener{
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)

        }

        userBackBtn = findViewById(R.id.userMgtBackBtn)

        userBackBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

        allUserBtn = findViewById(R.id.allUserBtn)

        allUserBtn.setOnClickListener{
            val intent = Intent(this, AllProfilesActivity::class.java)
            startActivity(intent)

        }

//        binding.userMgtSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                searchList(newText)
//                return true
//            }
//
//        })
    }


//    fun searchList(text:String){
//        val searchList = ArrayList<UserModel>()
//        for(userModel in userList){
//            if(userModel.name?.lowercase()?.contains(text.lowercase()) == true){
//                searchList.add(userModel)
//            }
//        }
//        adapter.searchDataList((searchList))
//    }

    private fun getUsersData() {
        userRecyclerView.visibility = View.GONE
//        tvLoadingData.visibility = View.VISIBLE

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

                    mAdapter.setOnItemClickListener(object : UserAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingActivity, UserDetailsActivity::class.java)
                            intent.putExtra("userId",userList[position].userId)
                            intent.putExtra("name",userList[position].name)
                            intent.putExtra("address",userList[position].address)
                            intent.putExtra("age",userList[position].age)
                            intent.putExtra("number",userList[position].number)
                            intent.putExtra("email",userList[position].email)
                            intent.putExtra("gender",userList[position].gender)
                            intent.putExtra("payment",userList[position].payment)
                            startActivity(intent)
                        }

                    })

                    userRecyclerView.visibility = View.VISIBLE
//                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}

