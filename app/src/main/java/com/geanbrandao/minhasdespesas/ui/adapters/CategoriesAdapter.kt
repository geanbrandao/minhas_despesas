package com.geanbrandao.minhasdespesas.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.modal.database.entity_categories.CategoriesData
import kotlinx.android.synthetic.main.item_category.view.*

class CategoriesAdapter(
        private val context: Context,
        private val data: ArrayList<CategoriesData> = arrayListOf()
) : RecyclerView.Adapter<CategoriesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data[position]

        holder.bindView(item)
    }

    override fun getItemCount() = data.count()

    fun clear() {
        this.data.clear()
        notifyDataSetChanged()
    }

    fun addAll(data: ArrayList<CategoriesData>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun add(data: CategoriesData) {
        this.data.add(data)
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val category = itemView.text_category as AppCompatTextView

        fun bindView(item: CategoriesData) {
            category.text = item.name
            val icon = ContextCompat.getDrawable(itemView.context, item.icon)!!
            val h: Int = icon.intrinsicHeight
            val w: Int = icon.intrinsicWidth
            icon.setBounds(0, 0, w, h)
            icon.setTint(ContextCompat.getColor(itemView.context, R.color.colorAccent))
            category.setCompoundDrawablesRelative(icon, null, null, null)
        }
    }
}
