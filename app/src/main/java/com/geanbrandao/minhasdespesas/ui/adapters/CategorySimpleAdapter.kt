package com.geanbrandao.minhasdespesas.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.getIconFromString
import com.geanbrandao.minhasdespesas.model.Category
import kotlinx.android.synthetic.main.item_simple_category.view.*

class CategorySimpleAdapter(
    private val context: Context,
    private val onClick: (() -> Unit),
    val data: ArrayList<Category> = arrayListOf()
) : RecyclerView.Adapter<CategorySimpleAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_simple_category, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data[position]

        holder.itemView.setOnClickListener {
            onClick.invoke()
        }

        holder.bindView(item)
    }

    override fun getItemCount() = data.count()

    fun clear() {
        this.data.clear()
        notifyDataSetChanged()
    }

    fun addAll(data: ArrayList<Category>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun add(data: Category) {
        this.data.add(data)
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val category = itemView.text_category_name as AppCompatTextView

        private val context = itemView.context

        fun bindView(item: Category) {
            val drawable = context.getIconFromString(item.icon, R.color.colorWhite)
            category.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null)
            category.text = item.name
        }
    }
}