package com.geanbrandao.minhasdespesas.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.geanbrandao.minhasdespesas.*
import com.geanbrandao.minhasdespesas.databinding.ItemDetailsHorizontalBinding
import com.geanbrandao.minhasdespesas.model.CategoriesExpenses
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.imageResource
import timber.log.Timber

class DetailsHorizontalAdapter(
    private val context: Context,
    private val onClickItem: (item: CategoriesExpenses) -> Unit,
    private val totalMonthSpent: Float,
    private val data: ArrayList<CategoriesExpenses>,
) : RecyclerView.Adapter<DetailsHorizontalAdapter.DetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_details_horizontal, parent, false)

        return DetailsViewHolder(view, onClickItem)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        val item = data[position]

        with(holder) {
            bindView(item, totalMonthSpent)
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

    class DetailsViewHolder(
        itemView: View,
        val onClickItem: (item: CategoriesExpenses) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val context = itemView.context
        val binding = ItemDetailsHorizontalBinding.bind(itemView)

        @SuppressLint("RestrictedApi")
        fun bindView(item: CategoriesExpenses, totalMonthSpent: Float) {
            with(item) {
                binding.imageButtonIcon.background = context.getIconFromString(item.category.icon, R.color.colorWhite)
                val drawable = context.getIconFromString("shape_outline", context.getColorFromString(item.category.colorName))
                binding.imageButtonBg.background = drawable

                binding.textCategoryName.text = item.category.name
                binding.textLaunchsCount.text = context.getString(R.string.item_details_text_count_launchs, item.countSpentCategory)
                binding.textValue.text = context.getString(R.string.text_default_value_br, item.valueSpentCategory)

                val percentage = item.valueSpentCategory.getPercentOfTotal(totalMonthSpent)
                Timber.d("DEBUG1 - $percentage")
                binding.textPercentage.text = context.getString(R.string.text_default_value_percentage, percentage)

                itemView.setOnClickListener {
                    onClickItem(this)
                }
            }
        }
    }
}