package com.example.mad_business_android_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase

class UserDetailsActivity : AppCompatActivity() {
    private lateinit var userProfId : TextView
    private lateinit var userProfName : TextView
    private lateinit var userProfAdd : TextView
    private lateinit var userProfAge : TextView
    private lateinit var userProfPhone : TextView
    private lateinit var userProfGender : TextView
    private lateinit var userProfMail : TextView
    private lateinit var userProfPay : TextView
    private lateinit var updatePrfBtn : Button
    private lateinit var deletePrfBtn : Button
    private lateinit var prfBackBtn : FloatingActionButton
//    private lateinit var updateBackBtn : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        initView()
        setValuesToView()

        updatePrfBtn.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("userId").toString(),
                intent.getStringExtra("name").toString(),
                intent.getStringExtra("gender").toString(),
                intent.getStringExtra("payment").toString()
            )
        }

        deletePrfBtn.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("userId").toString()
            )
        }

        prfBackBtn.setOnClickListener{
            val intent = Intent(this, FetchingActivity::class.java)
            startActivity(intent)
        }

//        updateBackBtn.setOnClickListener{
//            val intent = Intent(this, FetchingActivity::class.java)
//            startActivity(intent)
//
//        }

    }

    private fun deleteRecord(
        id : String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Users").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"User deleted successfully", Toast.LENGTH_LONG).show()
            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { error ->
            Toast.makeText(this,"Failed to delete user", Toast.LENGTH_LONG).show()
        }
    }

    private fun initView(){
        userProfId = findViewById(R.id.userProfIDTwo)
        userProfName = findViewById(R.id.userProfNameTwo)
        userProfAdd = findViewById(R.id.userProfAddTwo)
        userProfAge = findViewById(R.id.userProfAgeTwo)
        userProfPhone = findViewById(R.id.userProfPhoneTwo)
        userProfGender = findViewById(R.id.userProfGenderTwo)
        userProfMail = findViewById(R.id.userProfMailTwo)
        userProfPay = findViewById(R.id.userProfPayTwo)
        updatePrfBtn = findViewById(R.id.userProfUpdateBtn)
        deletePrfBtn = findViewById(R.id.userProfDeleteBtn)
        prfBackBtn = findViewById(R.id.userProfBackBtn)
//        updateBackBtn = findViewById(R.id.userProfUpdateBackBtn)

    }

    private fun setValuesToView(){
        userProfId.text= intent.getStringExtra("userId")
        userProfName.text= intent.getStringExtra("name")
        userProfAdd.text= intent.getStringExtra("address")
        userProfAge.text= intent.getStringExtra("age")
        userProfGender.text= intent.getStringExtra("gender")
        userProfMail.text= intent.getStringExtra("email")
        userProfPay.text= intent.getStringExtra("payment")
        userProfPhone.text= intent.getStringExtra("number")
    }

    private fun openUpdateDialog(
        userId : String,
        name : String,
        gender:String,
        payment:String,
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_user_mgt_update,null)
        mDialog.setView(mDialogView)

        val userName = mDialogView.findViewById<EditText>(R.id.userUpdateName)
        val userAge = mDialogView.findViewById<EditText>(R.id.userUpdateAge)
        val userAddress = mDialogView.findViewById<EditText>(R.id.userUpdateAddress)
        val userNumber = mDialogView.findViewById<EditText>(R.id.userUpdateTel)
        val userMail = mDialogView.findViewById<EditText>(R.id.userUpdateEmail)
        val btnUpdate = mDialogView.findViewById<Button>(R.id.updateUserBtn)

        userName.setText(intent.getStringExtra("name").toString())
        userAge.setText(intent.getStringExtra("age").toString())
        userAddress.setText(intent.getStringExtra("address").toString())
        userNumber.setText(intent.getStringExtra("number").toString())
        userMail.setText(intent.getStringExtra("email").toString())

        mDialog.setTitle("Updating $name Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdate.setOnClickListener(){
            updateUserData(
                userId,
                userName.text.toString(),
                userAddress.text.toString(),
                userAge.text.toString(),
                userNumber.text.toString(),
                userMail.text.toString(),
                gender,
                payment
            )
            Toast.makeText(applicationContext,"User Data Updated",Toast.LENGTH_LONG).show()

            // we are setting updated data to our textviews
            userProfName.text= userName.text.toString()
            userProfAdd.text= userAddress.text.toString()
            userProfAge.text= userAge.text.toString()
            userProfPhone.text= userNumber.text.toString()
            userProfMail.text= userMail.text.toString()

            alertDialog.dismiss()
        }

    }
    private fun updateUserData(
        userId:String,
        name:String,
        address:String,
        age:String,
        number:String,
        email:String,
        gender: String,
        payment: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Users").child(userId)
        val userInfo = UserModel(userId,name, address, age, number,email,gender,payment)
        dbRef.setValue(userInfo)
    }
}