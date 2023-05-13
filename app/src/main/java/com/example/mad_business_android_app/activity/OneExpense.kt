package com.example.mad_business_android_app.activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mad_business_android_app.R
import com.example.mad_business_android_app.databinding.HometabsBinding
import com.example.mad_business_android_app.models.Expenses
import com.google.firebase.database.FirebaseDatabase


class OneExpense : AppCompatActivity() {

    private lateinit var eExpName: TextView
    private lateinit var eExpId: TextView
    private lateinit var eExpAmount: TextView
    private lateinit var eExpDate: TextView
    private lateinit var eExpCategory: Spinner
    private lateinit var eExpUpdate: Button
    private lateinit var eExpDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_one_expense)

        initView()
        setValuesToViews()

        //backbutton
        val back = findViewById<ImageView>(R.id.backbtn2)
        back.setOnClickListener{
            val intent=Intent(this, ExpenseHome::class.java)
        }


        //update
        eExpUpdate.setOnClickListener {
            openUpdateDialog(

                intent.getStringExtra("id").toString(),
                intent.getStringExtra("name").toString(),
                intent.getStringExtra("date").toString(),
                intent.getStringExtra("amount").toString(),
                intent.getStringExtra("category").toString()
            )
        }

        //delete record
        eExpDelete.setOnClickListener{
                deleteRecord(
                    intent.getStringExtra("id").toString()
                )
        }
        //Going back
        val viewHistory = findViewById<ImageView>(R.id.backbtn2)
        viewHistory.setOnClickListener {
            val intent = Intent(this, ExpenseHome::class.java)
            startActivity(intent)
        }


    }

    private fun deleteRecord(id: String) {
            val dbRef = FirebaseDatabase.getInstance().getReference("Expenses").child(id)
            val expTask = dbRef.removeValue()

        expTask.addOnSuccessListener {
            Toast.makeText(this,"Exense data deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this, ExpenseHome::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{
            Toast.makeText(this, "Deleting Error", Toast.LENGTH_SHORT).show()
        }

    }

    private fun initView() {


        eExpId = findViewById(R.id.view_expId)
        eExpAmount = findViewById(R.id.view_amount)
        eExpName = findViewById(R.id.view_name)
        eExpDate = findViewById(R.id.update_date1)
        eExpCategory = findViewById(R.id.view_cat)
        eExpUpdate = findViewById(R.id.exp_edit)
        eExpDelete = findViewById(R.id.exp_delete)

    }

    private fun setValuesToViews(){
        eExpId.text = intent.getStringExtra("id")
        eExpName.text = intent.getStringExtra("name")
        eExpAmount.text = intent.getStringExtra("amount")
        eExpDate.text = intent.getStringExtra("date")

        val selectedCategory = intent.getStringExtra("category")

        val categories = resources.getStringArray(R.array.expense_cat)
        val categoryIndex = categories.indexOf(selectedCategory)
        eExpCategory.setSelection(categoryIndex)


    }

    private fun openUpdateDialog(
        id:String,
        name: String,
        date: String,
        amount: String,
        category: String
    ) {

        val expDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val expDialogView = inflater.inflate(R.layout.activity_expense_update, null)

        expDialog.setView(expDialogView)

        val etExpName = expDialogView.findViewById<EditText>(R.id.update_name)
        val etExpAmount = expDialogView.findViewById<EditText>(R.id.update_amount)
        val etExpDate= expDialogView.findViewById<EditText>(R.id.update_date1)
        val etExpCat = expDialogView.findViewById<Spinner>(R.id.update_cat)
        val expUpdateBtn = expDialogView.findViewById<Button>(R.id.update_edit)

        etExpName.setText(intent.getStringExtra("name").toString())
        etExpAmount.setText(intent.getStringExtra("amount").toString())
        etExpDate.setText(intent.getStringExtra("date").toString())

        val selectedCategory = intent.getStringExtra("category")
        val categories = resources.getStringArray(R.array.expense_cat)
        val categoryIndex = categories.indexOf(selectedCategory)
        etExpCat.setSelection(categoryIndex)

        expDialog.setTitle("Update $name Record")

        val alertDialog = expDialog.create()
        alertDialog.show()

        expUpdateBtn.setOnClickListener {
          updateExpenseData(
                id,
                etExpName.text.toString(),
                etExpAmount.text.toString(),
                etExpDate.text.toString(),
                etExpCat.selectedItem.toString()
            )

            Toast.makeText(applicationContext,"Employee Data Updated", Toast.LENGTH_LONG).show()

            eExpAmount.text = etExpAmount.text.toString()
            eExpName.text = etExpName.text.toString()
            eExpDate.text = etExpDate.text.toString()
            val selectedCat = etExpCat.selectedItem.toString()
            val categories = resources.getStringArray(R.array.expense_cat)
            val categoryId = categories.indexOf(selectedCat)
            eExpCategory.setSelection(categoryId)


            alertDialog.dismiss()

        }

    }

    private fun updateExpenseData(
        id:String,
        name :String,
        amount:String,
        date:String,
        category:String

    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Expenses").child(id)
        val expInfo = Expenses(id,amount,name,date,category)
        dbRef.setValue(expInfo)


    }

}



