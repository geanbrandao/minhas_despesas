package com.geanbrandao.minhasdespesas.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.databinding.ItemBarVerticalBinding
import com.geanbrandao.minhasdespesas.utils.RandomColors
import kotlin.random.Random

class BarsVerticalAdapter(
    private val context: Context,
    private val onClickItem: (item: String) -> Unit,
    private val data: ArrayList<String> = arrayListOf()
) : RecyclerView.Adapter<BarsVerticalAdapter.BarsVerticalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarsVerticalViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_bar_vertical, parent, false)

        return BarsVerticalViewHolder(view, onClickItem)
    }

    override fun onBindViewHolder(holder: BarsVerticalViewHolder, position: Int) {
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

    class BarsVerticalViewHolder(
        val itemView: View,
        val onClickItem: (item: String) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val context = itemView.context
        val binding = ItemBarVerticalBinding.bind(itemView)

        fun bindView(item: String) {
            setMaxAndProgress(max = 100f, progress = Random.nextInt(0, 100).toFloat())
            itemView.setOnClickListener {
                onClickItem(item)
            }
        }

        private fun setMaxAndProgress(max: Float, progress: Float) {
            binding.progressbar.highlightView.color = RandomColors().color
            binding.progressbar.max = max
            binding.progressbar.progress = progress
        }
    }
}