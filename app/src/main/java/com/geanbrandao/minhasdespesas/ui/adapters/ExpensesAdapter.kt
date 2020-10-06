package com.geanbrandao.minhasdespesas.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.getDayNumber
import com.geanbrandao.minhasdespesas.getMonth3LettersName
import com.geanbrandao.minhasdespesas.modal.database.expenses.ExpensesData
import kotlinx.android.synthetic.main.item_expense.view.*

class ExpensesAdapter (
    private val context: Context,
    private val onClick: ((item: ExpensesData) -> Unit),
    private val data: ArrayList<ExpensesData> = arrayListOf()
): RecyclerView.Adapter<ExpensesAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_expense, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data[position]

        holder.itemView.setOnClickListener {
            onClick.invoke(item)
        }

        holder.bindView(item)
    }

    override fun getItemCount() = data.size

    fun clear() {
        this.data.clear()
        notifyDataSetChanged()
    }

    fun addAll(data: ArrayList<ExpensesData>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.text_title as AppCompatTextView
        val month = itemView.text_month as AppCompatTextView
        val day = itemView.text_day as AppCompatTextView
        val amount = itemView.text_amount_spent as AppCompatTextView


        fun bindView(item: ExpensesData) {
            title.text = item.title
            month.text = item.date.getMonth3LettersName()
            day.text = item.date.getDayNumber()
//            amount.text = "R$ %.2f".format(item.amount)
            amount.text = itemView.context.getString(R.string.text_value_money, item.amount)
        }

    }
}