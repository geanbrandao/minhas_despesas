package com.geanbrandao.minhasdespesas.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.geanbrandao.minhasdespesas.*
import com.geanbrandao.minhasdespesas.databinding.ItemBarVerticalBinding
import com.geanbrandao.minhasdespesas.model.CategoriesExpenses
import com.geanbrandao.minhasdespesas.utils.RandomColors
import org.jetbrains.anko.backgroundColor
import kotlin.random.Random

class BarsVerticalAdapter(
    private val context: Context,
    private val onClickItem: (item: CategoriesExpenses) -> Unit,
    private val maxSpentCategory: Float,
    private val data: ArrayList<CategoriesExpenses>,
) : RecyclerView.Adapter<BarsVerticalAdapter.BarsVerticalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarsVerticalViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_bar_vertical, parent, false)

        val binding = ItemBarVerticalBinding.bind(view)
        val lp = binding.progressbar.layoutParams
        lp.height = context.getScreenHeight(Keys.KEY_VERTICAL_BAR_SIZE).toInt()
        binding.progressbar.layoutParams = lp

        return BarsVerticalViewHolder(view, onClickItem)
    }

    override fun onBindViewHolder(holder: BarsVerticalViewHolder, position: Int) {
        val item = data[position]

        with(holder) {
            bindView(item, maxSpentCategory)
        }
    }

    override fun getItemCount() = data.count()

    fun clear() {
        this.data.clear()
        notifyDataSetChanged()
    }

    fun add(item: CategoriesExpenses) {
        this.data.add(item)
        notifyDataSetChanged()
    }

    fun addAll(data: ArrayList<CategoriesExpenses>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class BarsVerticalViewHolder(
        itemView: View,
        val onClickItem: (item: CategoriesExpenses) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val context = itemView.context
        val binding = ItemBarVerticalBinding.bind(itemView)

        fun bindView(item: CategoriesExpenses, maxSpentCategory: Float) {
            binding.imageIcon.setImageDrawable(context.getIconFromString(item.category.icon, R.color.colorWhite))
            val colorId = context.getColorFromString(item.category.colorName)
//            binding.imageIconBg.setImageDrawable(context.getIconFromString(item.category.icon, colorId))
            setMaxAndProgress(maxSpentCategory, item.valueSpentCategory, colorId)
            itemView.setOnClickListener {
                onClickItem(item)
            }
        }

        private fun setMaxAndProgress(max: Float, progress: Float, colorBar: Int) {
            binding.progressbar.highlightView.color = ContextCompat.getColor(context, colorBar)
            binding.progressbar.max = max
            binding.progressbar.progress = progress
        }
    }
}