package com.geanbrandao.minhasdespesas.ui.statistics

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.databinding.ActivityStatisticsBinding
import com.geanbrandao.minhasdespesas.increaseHitArea
import com.geanbrandao.minhasdespesas.ui.adapters.AdapterPager
import com.geanbrandao.minhasdespesas.ui.base.activity.BaseActivity
import com.geanbrandao.minhasdespesas.utils.CustomLinearLayoutManager


class StatisticsActivity : BaseActivity() {

    private lateinit var binding: ActivityStatisticsBinding

    private val adapter: AdapterPager by lazy {
        AdapterPager(this)
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

    private fun setupRecycler() {
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerStatistics)
        binding.recyclerStatistics.isNestedScrollingEnabled = false
        val  linearLayoutManager = CustomLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerStatistics.layoutManager = linearLayoutManager
        binding.recyclerStatistics.adapter = adapter


        // just for now
        adapter.addAll(arrayListOf("Março", "Abril", "Maio", "Março", "Abril", "Maio"))
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