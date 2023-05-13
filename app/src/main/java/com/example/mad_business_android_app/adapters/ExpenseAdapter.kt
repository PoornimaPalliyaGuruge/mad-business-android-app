package com.example.mad_business_android_app.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mad_business_android_app.R
import com.example.mad_business_android_app.models.Expenses
import java.util.ArrayList


class ExpenseAdapter(private var ExpList: ArrayList<Expenses>)
    : RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    private lateinit var expListner: OnItemClickListner

    interface OnItemClickListner {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListner(clickListner: OnItemClickListner) {
        expListner = clickListner
    }

    fun searchDataList(searchList: ArrayList<Expenses>) {
        ExpList = searchList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_of_expenses, parent, false)
        return ViewHolder(itemView, expListner!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentExpense = ExpList[position]
        holder.tvExpName.text = currentExpense.name
        holder.tvExpAmount.text = currentExpense.amount
        holder.tvExpDate.text = currentExpense.date
    }

    override fun getItemCount(): Int {
        return ExpList.size
    }

    class ViewHolder(itemView: View, clickListner: OnItemClickListner) : RecyclerView.ViewHolder(itemView) {
        val tvExpName: TextView = itemView.findViewById(R.id.ViewExpName)
        val tvExpAmount: TextView = itemView.findViewById(R.id.ViewExpAmount)
        val tvExpDate: TextView = itemView.findViewById(R.id.ViewExpDate)

        init {
            itemView.setOnClickListener {
                clickListner.onItemClick(adapterPosition)
            }
        }
    }
}
