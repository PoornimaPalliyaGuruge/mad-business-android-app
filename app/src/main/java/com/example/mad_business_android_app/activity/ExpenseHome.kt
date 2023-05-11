package com.example.mad_business_android_app.activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mad_business_android_app.ExpenseHistory
import com.example.mad_business_android_app.adapters.ExpenseAdapter
import com.example.mad_business_android_app.models.Expenses
import com.google.firebase.database.*
import com.example.mad_business_android_app.R
import org.w3c.dom.Text
import java.util.*
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.collections.ArrayList


class ExpenseHome : AppCompatActivity() {

    private lateinit var expRecyclerView: RecyclerView
    private lateinit var expList : ArrayList<Expenses>
    private lateinit var dbRef  : DatabaseReference
    private lateinit var searchView : SearchView
    private lateinit var filteredList: ArrayList<Expenses>
    private lateinit var totExpense : TextView
    private lateinit var month : TextView
    private lateinit var date : TextView

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

        //get the total spending
        totExpense = findViewById(R.id.totExpense)

        //get date and time
        date = findViewById(R.id.date)
        month = findViewById(R.id.month)

        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val monthFormat = SimpleDateFormat("MMMM", Locale.getDefault())
        val currentMonth = monthFormat.format(calendar.time)

        date.text = "$currentDay"
        month.text = "$currentMonth"

        expRecyclerView = findViewById(R.id.recyclerView)
        expRecyclerView.layoutManager = GridLayoutManager(this,1)
        expRecyclerView.hasFixedSize()
        expRecyclerView.clearFocus()

        expList =  arrayListOf()
        filteredList = arrayListOf()

        searchView = findViewById<SearchView>(R.id.searchExp)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    filteredList.clear()
                    val searchQuery = newText.toLowerCase(Locale.getDefault())
//                    val searchQuery = newText?.toLowerCase(Locale.getDefault()) ?: ""

                    if (searchQuery.isNotEmpty()) {
                        for (data in expList) {
                            if (data.name?.toLowerCase(Locale.getDefault())?.contains(searchQuery) == true) {
                                filteredList.add(data)
                            }
                        }
                        val mAdapter = ExpenseAdapter(filteredList)
                        mAdapter.setOnItemClickListner(object : ExpenseAdapter.OnItemClickListner {
                            override fun onItemClick(position: Int) {
                                val intent = Intent(this@ExpenseHome, OneExpense::class.java)
                                //put extras
                                intent.putExtra("id", filteredList[position].id)
                                intent.putExtra("name", filteredList[position].name)
                                intent.putExtra("amount", filteredList[position].amount)
                                intent.putExtra("date", filteredList[position].date)
                                intent.putExtra("category", filteredList[position].category)
                                startActivity(intent)
                            }
                        })
                        expRecyclerView.adapter = mAdapter
                    } else {
                        val mAdapter = ExpenseAdapter(expList)
                        mAdapter.setOnItemClickListner(object : ExpenseAdapter.OnItemClickListner {
                            override fun onItemClick(position: Int) {
                                val intent = Intent(this@ExpenseHome, OneExpense::class.java)
                                //put extras
                                intent.putExtra("id", expList[position].id)
                                intent.putExtra("name", expList[position].name)
                                intent.putExtra("amount", expList[position].amount)
                                intent.putExtra("date", expList[position].date)
                                intent.putExtra("category", expList[position].category)
                                startActivity(intent)
                            }
                        })
                        expRecyclerView.adapter = mAdapter
                    }
                }
                return true
            }

        })

        getExpensesData()



    }

    private fun getExpensesData(){
        dbRef = FirebaseDatabase.getInstance().getReference("Expenses")


        dbRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                expList.clear()

                var totalAmount = 0.0
                if(snapshot.exists()){
                    for(expSnap in snapshot.children){
                        val expData = expSnap.getValue(Expenses::class.java)
                        expList.add(expData!!)
                        totalAmount += expData.amount!!.toDouble()
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
                    totExpense.text = "     $totalAmount"
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })



    }


}


