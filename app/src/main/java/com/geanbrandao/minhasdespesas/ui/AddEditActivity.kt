package com.geanbrandao.minhasdespesas.ui

import android.os.Bundle
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.increaseHitArea
import com.geanbrandao.minhasdespesas.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_edit.*
import kotlinx.android.synthetic.main.component_toolbar.view.*

class AddEditActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        createListeners()
    }

    private fun createListeners() {
        setupToolbar()
    }

    private fun setupToolbar() {
        toolbar.title.text = getString(R.string.add_edit_activity_title_page)

        toolbar.back.increaseHitArea(20f)
        toolbar.back.setOnClickListener {
            stepBefore()
        }
    }

    private fun stepBefore() {
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        stepBefore()
    }
}