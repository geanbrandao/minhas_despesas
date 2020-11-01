package com.geanbrandao.minhasdespesas.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.geanbrandao.minhasdespesas.model.MonthExpenseReport
import com.geanbrandao.minhasdespesas.ui.statistics.PagerFragment
import timber.log.Timber

class AdapterPager(
    fragmentActivity: FragmentActivity,
    private val data: ArrayList<MonthExpenseReport>
) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        Timber.d("DEBUG1 - createFragment")
        return PagerFragment.newInstance(data[position])
    }

    override fun getItemCount() = data.count()
}