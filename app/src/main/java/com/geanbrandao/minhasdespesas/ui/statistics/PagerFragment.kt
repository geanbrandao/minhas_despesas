package com.geanbrandao.minhasdespesas.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.geanbrandao.minhasdespesas.Keys
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.databinding.PageMonthStatisticsBinding
import com.geanbrandao.minhasdespesas.getScreenHeight
import com.geanbrandao.minhasdespesas.model.CategoriesExpenses
import com.geanbrandao.minhasdespesas.model.MonthExpenseReport
import com.geanbrandao.minhasdespesas.ui.adapters.BarsVerticalAdapter
import com.geanbrandao.minhasdespesas.ui.adapters.DetailsHorizontalAdapter
import timber.log.Timber


class PagerFragment : Fragment() {

    companion object {
        fun newInstance(monthExpenseReport: MonthExpenseReport): PagerFragment {
            val fragment = PagerFragment()
            Timber.d("DEBUG1 - putSerializable")

            val args = Bundle()
            args.putSerializable(KEY_EXPENSES_REPORT, monthExpenseReport)
            fragment.arguments = args
            Timber.d("DEBUG1 - putSerializable")
            return fragment
        }

        private const val KEY_EXPENSES_REPORT = "EXPENSES_REPORT"
    }

    private lateinit var binding: PageMonthStatisticsBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = PageMonthStatisticsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var report: MonthExpenseReport? = null
        arguments?.takeIf { it.containsKey(KEY_EXPENSES_REPORT) }?.apply {
            Timber.d("DEBUG1 - ARGUMENTS")
            report = getSerializable(KEY_EXPENSES_REPORT) as MonthExpenseReport?
        }
        report?.let {
            Timber.d("DEBUG1 - BINDVIEW")
            bindView(it)
        }
    }


    private fun bindView(item: MonthExpenseReport) {
        binding.textMonth.text = item.monthTitle

        var totalMonthSpent = 0f
        item.categoriesExpenses.forEach { totalMonthSpent += it.valueSpentCategory }
        binding.textMonthValue.text = getString(R.string.text_default_value_br, totalMonthSpent)

        val arraySorted = item.categoriesExpenses.sortedWith(compareByDescending {
            it.valueSpentCategory
        })

        val maxValueSpentCategory = arraySorted.maxOf {
            it.valueSpentCategory
        }

        setupRecyclerBarsVertical(arraySorted.toCollection(ArrayList()), maxValueSpentCategory)
        setupRecyclerBarsHorizontal(arraySorted.toCollection(ArrayList()), totalMonthSpent)

    }

    private fun setupRecyclerBarsVertical(data: ArrayList<CategoriesExpenses>, maxValueSpentCategory: Float) {
        val adapterBarsVertical = BarsVerticalAdapter(
                requireContext(),
                {

                },
                maxValueSpentCategory,
                data,
        )

        val lp = binding.recyclerBarsVertical.layoutParams
        lp.height = requireContext().getScreenHeight(Keys.KEY_VERTICAL_BAR_SIZE).toInt()
        binding.recyclerBarsVertical.layoutParams = lp

        binding.recyclerBarsVertical.adapter = adapterBarsVertical

        binding.recyclerBarsVertical.addOnItemTouchListener(object : OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                when (e.action) {
                    MotionEvent.ACTION_MOVE -> rv.parent.requestDisallowInterceptTouchEvent(true)
                }
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })

    }

    private fun setupRecyclerBarsHorizontal(data: ArrayList<CategoriesExpenses>, totalMonthSpent: Float) {
        val adapterDetailsHorizontal = DetailsHorizontalAdapter(
                requireContext(),
                {

                },
                totalMonthSpent,
                data,
        )
        binding.recyclerBarsHorizontal.adapter = adapterDetailsHorizontal
    }

}