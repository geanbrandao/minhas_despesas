package com.geanbrandao.minhasdespesas.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.getIconFromString
import com.geanbrandao.minhasdespesas.model.Category
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(
    private val context: Context,
    private val onCheckedChange: ((item: Category, isChecked: Boolean) -> Unit),
    private val onClick: ((item: Category) -> Unit),
    private val data: ArrayList<Category> = arrayListOf()
) : RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data[position]

        holder.bindView(item)

        holder.checkbox.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(!compoundButton.isPressed) {
                return@setOnCheckedChangeListener
            }
            onCheckedChange.invoke(item, isChecked)
            data[position].isSelected = isChecked
        }

        holder.category.setOnClickListener {
            if (item.canRemove) {
                onClick.invoke(item)
            }
        }
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

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val category = itemView.text_category as AppCompatTextView
        val checkbox = itemView.checkbox_category as AppCompatCheckBox

        private val context = itemView.context

        fun bindView(item: Category) {
            category.text = item.name
            val icon = context.getIconFromString(item.icon, R.color.colorAccent)
            category.setCompoundDrawablesRelative(icon, null, null, null)
            checkbox.isChecked = item.isSelected
        }
    }
}
