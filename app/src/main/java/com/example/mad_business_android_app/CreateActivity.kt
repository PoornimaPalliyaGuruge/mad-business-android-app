package com.example.mad_business_android_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mad_business_android_app.models.UserModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateActivity : AppCompatActivity() {

    val payments = arrayOf("Card","Cash","Paypal")
    val gender = arrayOf("Male","Female")

    private lateinit var cusName: EditText
    private lateinit var cusAddress: EditText
    private lateinit var cusAge: EditText
    private lateinit var cusNumber: EditText
    private lateinit var cusEmail: EditText
    private lateinit var cusGender : Spinner
    private lateinit var cusPayment: Spinner
    private lateinit var btnSave: Button
    private lateinit var userBackBtn: FloatingActionButton

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_mgt_create)

        cusName = findViewById(R.id.userCreateName)
        cusAddress = findViewById(R.id.userCreateAddress)
        cusAge = findViewById(R.id.userCreateAge)
        cusNumber = findViewById(R.id.userCreateTel)
        cusEmail = findViewById(R.id.userCrtEmail)
        cusGender = findViewById(R.id.userCreateGenderSpinner)
        val arrayAdapterGender = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,gender)
        cusGender.adapter = arrayAdapterGender
        cusGender.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view:View?,position:Int,id:Long) {
                Toast.makeText(applicationContext,"gender type is "+gender[position],Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        cusPayment = findViewById(R.id.userCreatePaymentSpinner)
        val arrayAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,payments)
        cusPayment.adapter = arrayAdapter
        cusPayment.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view:View?,position:Int,id:Long) {
                Toast.makeText(applicationContext,"payment type is "+payments[position],Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


        btnSave = findViewById(R.id.userCreateBtnTwo)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        btnSave.setOnClickListener{
            saveUserData()
        }

        userBackBtn = findViewById(R.id.userCreateBackBtn)

        userBackBtn.setOnClickListener{
            val intent = Intent(this, FetchingActivity::class.java)
            startActivity(intent)

        }
    }

    private fun saveUserData(){

        //getting values
        val name = cusName.text.toString()
        val address = cusAddress.text.toString()
        val age = cusAge.text.toString()
        val number = cusNumber.text.toString()
        val email = cusEmail.text.toString()
//        val female = cusFemale.isChecked().toString()
//        val male = cusMale.isChecked().toString()
        val gender = cusGender.getSelectedItem().toString()
        val payment = cusPayment.getSelectedItem().toString()

        if(name.isEmpty()){
            cusName.error = "Please enter name"
        }else if(email.isEmpty()){
            cusEmail.error = "Please enter email"
        }

        val userId = dbRef.push().key!!

        val user = UserModel(userId,name,address,age,number,email,gender,payment)

        dbRef.child(userId).setValue(user)
            .addOnCompleteListener{
                Toast.makeText(this,"User created successfully", Toast.LENGTH_LONG).show()
                val intent = Intent(this, FetchingActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener { err ->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_LONG).show()
            }


    }
}