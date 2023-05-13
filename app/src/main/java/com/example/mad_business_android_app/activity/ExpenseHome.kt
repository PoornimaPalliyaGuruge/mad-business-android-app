package com.example.mad_business_android_app.activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mad_business_android_app.ExpenseHistory
import com.example.mad_business_android_app.adapters.ExpenseAdapter
import com.example.mad_business_android_app.models.Expenses
import com.google.firebase.database.*
import com.example.mad_business_android_app.R
import com.google.firebase.ktx.Firebase


class ExpenseHome : AppCompatActivity() {

    private lateinit var expRecyclerView: RecyclerView
    private lateinit var expList : ArrayList<Expenses>
    private lateinit var dbRef  : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.expense_home)

        //adding new expense -->
        val addNewExpenseImageView = findViewById<ImageView>(R.id.addNewExpense)
        addNewExpenseImageView.setOnClickListener {
            val intent = Intent(this, AddNewExpense::class.java)
            startActivity(intent)
        }


        //Viewing History
        val viewHistory = findViewById<ImageView>(R.id.history)
        viewHistory.setOnClickListener {
            val intent = Intent(this, ExpenseHistory::class.java)
            startActivity(intent)
        }

        expRecyclerView = findViewById(R.id.recyclerView)
        expRecyclerView.layoutManager = GridLayoutManager(this,1)
        expRecyclerView.hasFixedSize()

        expList =  arrayListOf<Expenses>()

        getExpensesData()
    }

    private fun getExpensesData(){
        dbRef = FirebaseDatabase.getInstance().getReference("Expenses")

        dbRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                expList.clear()
                if(snapshot.exists()){
                    for(expSnap in snapshot.children){
                        val expData = expSnap.getValue(Expenses::class.java)
                        expList.add(expData!!)
                    }

                    val mAdapter = ExpenseAdapter(expList)
                    expRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListner(object : ExpenseAdapter.OnItemClickListner{
                        override fun onItemClick(position: Int) {
                           val intent = Intent(this@ExpenseHome, OneExpense::class.java)

                            //put extras
                            intent.putExtra("id",expList[position].id)
                            intent.putExtra("name",expList[position].name)
                            intent.putExtra("amount",expList[position].amount)
                            intent.putExtra("date",expList[position].date)
                            intent.putExtra("category",expList[position].category)
                            startActivity(intent)

                        }

                    })

                    expRecyclerView.visibility= View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })



    }


}


