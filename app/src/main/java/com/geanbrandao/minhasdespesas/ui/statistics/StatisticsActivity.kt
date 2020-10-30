package com.geanbrandao.minhasdespesas.ui.statistics

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.databinding.ActivityStatisticsBinding
import com.geanbrandao.minhasdespesas.increaseHitArea
import com.geanbrandao.minhasdespesas.showToast
import com.geanbrandao.minhasdespesas.ui.adapters.AdapterPager
import com.geanbrandao.minhasdespesas.ui.base.activity.BaseActivity


class StatisticsActivity : BaseActivity() {

    private lateinit var binding: ActivityStatisticsBinding

    private val adapter: AdapterPager by lazy {
        AdapterPager(this,
            { position ->
                handlerNextPreviousPage(position)
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createListeners()
    }

    private fun createListeners() {
        setupToolbar()
        setupRecycler()
    }

    /**
     * make the initial setup for the recycler
     *
     */
    private fun setupRecycler() {
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerStatistics)

//        binding.recyclerStatistics.layoutManager =
//            CustomLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
//                reverseLayout = false,
//                canScroll = false)

        binding.recyclerStatistics

        binding.recyclerStatistics.adapter = adapter
        binding.recyclerStatistics.addOnScrollListener(object : RecyclerView.OnScrollListener() {

        })

        // just for now
        adapter.addAll(arrayListOf("Março", "Abril", "Maio", "Março", "Abril", "Maio"))

    }

    /**
     * Handles clicks to change the current showing page in the recycler
     *
     * @param position - positon to scroll
     */
    private fun handlerNextPreviousPage(position: Int) {
//        binding.recyclerStatistics.layoutManager =
//            CustomLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
//                reverseLayout = false,
//                canScroll = true)

        showToast("POSITION $position")
        binding.recyclerStatistics.post(Runnable {
            binding.recyclerStatistics.smoothScrollToPosition(position)
        })

//        binding.recyclerStatistics.layoutManager =
//            CustomLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
//                reverseLayout = false,
//                canScroll = false)

    }

    private fun setupToolbar() {
        binding.toolbar.title.text = getString(R.string.statistics_activity_title_page)

        binding.toolbar.back.increaseHitArea(20f)
        binding.toolbar.back.setOnClickListener {
            stepBefore()
        }
    }

    private fun stepBefore() {
        finish()
    }

    override fun onBackPressed() {
        stepBefore()
    }
}