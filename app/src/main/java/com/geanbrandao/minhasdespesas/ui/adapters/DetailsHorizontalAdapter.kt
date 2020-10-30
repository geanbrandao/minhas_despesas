package com.geanbrandao.minhasdespesas.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.databinding.ItemDetailsHorizontalBinding

class DetailsHorizontalAdapter(
    private val context: Context,
    private val onClickItem: (item: String) -> Unit,
    private val data: ArrayList<String> = arrayListOf()
) : RecyclerView.Adapter<DetailsHorizontalAdapter.DetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_details_horizontal, parent, false)

        return DetailsViewHolder(view, onClickItem)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        val item = data[position]

        with(holder) {
            bindView(item)
        }
    }

    override fun getItemCount() = data.count()

    fun clear() {
        this.data.clear()
        notifyDataSetChanged()
    }

    fun add(item: String) {
        this.data.add(item)
        notifyDataSetChanged()
    }

    fun addAll(data: ArrayList<String>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class DetailsViewHolder(val itemView: View, val onClickItem: (item: String) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        val binding = ItemDetailsHorizontalBinding.bind(itemView)

        fun bindView(item: String) {
            with(item) {
                itemView.setOnClickListener {
                    onClickItem(this)
                }
            }
        }
    }
}