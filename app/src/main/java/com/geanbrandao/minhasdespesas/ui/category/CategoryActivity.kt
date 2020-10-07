package com.geanbrandao.minhasdespesas.ui.category

import android.os.Bundle
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.increaseHitArea
import com.geanbrandao.minhasdespesas.showDialogMessage
import com.geanbrandao.minhasdespesas.ui.adapters.CategoriesAdapter
import com.geanbrandao.minhasdespesas.ui.base.activity.BaseActivity
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.component_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class CategoryActivity : BaseActivity() {

    private var disposable: Disposable? = null

    private val viewModel: CategoriesViewModel by viewModel()

    private val adapter: CategoriesAdapter by lazy {
        CategoriesAdapter(
                this,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        savedInstanceState?.let {
            getCategories()

        } ?: run {
            getCategories()
        }

        createListeners()
    }

    private fun createListeners() {
        setupToolbar()
        recycler_category.adapter = adapter
    }

    private fun getCategories() {
        disposable = viewModel.getAll(this).subscribeBy(
                onNext = {
                    adapter.clear()
                    it.forEach {
                        adapter.add(it)
                    }
                },
                onError = {
                    Timber.e(it)
                    showDialogMessage(getString(R.string.errors_generic))
                },
                onComplete = {}
        )
    }

    private fun setupToolbar() {
        toolbar.title.text = getString(R.string.category_activity_title_page)

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

    override fun onStop() {
        super.onStop()
        disposable?.dispose()
    }
}