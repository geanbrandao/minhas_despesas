package com.geanbrandao.minhasdespesas.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.databinding.PageMonthStatisticsBinding
import com.geanbrandao.minhasdespesas.setOnCompoudDrawableEndClickListener
import com.geanbrandao.minhasdespesas.setOnCompoudDrawableStartClickListener
import com.geanbrandao.minhasdespesas.showToast

class AdapterPager(
    private val context: Context,
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

            binding.textMonth.setOnCompoudDrawableEndClickListener {
                context.showToast("END")
            }

            binding.textMonth.setOnCompoudDrawableStartClickListener {
                context.showToast("START")
            }

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