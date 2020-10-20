package com.geanbrandao.minhasdespesas.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.model.Expense
import kotlinx.android.synthetic.main.item_bar.view.*

class BarAdapter(
    private val context: Context,
    private val onClick: (() -> Unit),
    private val data: ArrayList<Expense> = arrayListOf()
): RecyclerView.Adapter<BarAdapter.MyViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_bar, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

    override fun getItemCount() = data.count()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val progress = itemView.progressbar as ProgressBar

        fun bindView() {

        }
    }
}