package com.geanbrandao.minhasdespesas.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.geanbrandao.minhasdespesas.*
import com.geanbrandao.minhasdespesas.databinding.PageMonthStatisticsBinding

class AdapterPager(
    private val context: Context,
    private val onChangePageClick: ((position: Int) -> Unit),
    private val data: ArrayList<String> = arrayListOf()
) : RecyclerView.Adapter<AdapterPager.MyViewHoler>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHoler {
        val view =
            LayoutInflater.from(context).inflate(R.layout.page_month_statistics, parent, false)

        return MyViewHoler(view)
    }

    override fun onBindViewHolder(holder: MyViewHoler, position: Int) {
        val item = data[position]

        with(holder) {
            bindView(item)
            when (position) {
                0 -> {
                    binding.imagePrevious.hide()
                    binding.imageNext.show()
                }
                data.lastIndex -> {
                    binding.imagePrevious.show()
                    binding.imageNext.hide()
                }
                else -> {
                    binding.imagePrevious.show()
                    binding.imageNext.show()
                }
            }

            binding.imageNext.increaseHitArea(20f)
            binding.imagePrevious.increaseHitArea(20f)

            binding.imageNext.setOnClickListener {
                context.showToast("NEXT")
                onChangePageClick.invoke(position + 1)
            }

            binding.imagePrevious.setOnClickListener {
                context.showToast("PREVIOUS")
                onChangePageClick.invoke(position - 1)
            }
        }
    }

    override fun getItemCount() = data.count()

    fun addAll(data: ArrayList<String>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class MyViewHoler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = PageMonthStatisticsBinding.bind(itemView)
        val context = itemView.context

        private val adapterBarsVertical: BarsVerticalAdapter by lazy {
            BarsVerticalAdapter(context,
                {

                }
            )
        }

        private val adapterDetailsHorizontal: DetailsHorizontalAdapter by lazy {
            DetailsHorizontalAdapter(context,
                {

                }
            )
        }

        fun bindView(item: String) {

            binding.textMonth.text = item

            setupRecyclerBarsVertical()
            setupRecyclerBarsHorizontal()
        }

        private fun setupRecyclerBarsVertical() {
            binding.recyclerBarsVertical.adapter = adapterBarsVertical
            adapterBarsVertical.addAll(arrayListOf("", "", "","", "", "","", "", ""))
        }

        private fun setupRecyclerBarsHorizontal() {
            binding.recyclerBarsHorizontal.adapter = adapterDetailsHorizontal
            adapterDetailsHorizontal.addAll(arrayListOf("", "", "","", "", "","", "", ""))
        }
    }
}