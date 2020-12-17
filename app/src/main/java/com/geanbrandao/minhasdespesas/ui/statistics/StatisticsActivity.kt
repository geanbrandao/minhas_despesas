package com.geanbrandao.minhasdespesas.ui.statistics

import android.os.Bundle
import com.geanbrandao.minhasdespesas.*
import com.geanbrandao.minhasdespesas.databinding.ActivityStatisticsBinding
import com.geanbrandao.minhasdespesas.model.CategoriesExpenses
import com.geanbrandao.minhasdespesas.model.Category
import com.geanbrandao.minhasdespesas.model.Expense
import com.geanbrandao.minhasdespesas.model.MonthExpenseReport
import com.geanbrandao.minhasdespesas.model.database.entity_expenses.ExpensesData
import com.geanbrandao.minhasdespesas.ui.adapters.AdapterPager
import com.geanbrandao.minhasdespesas.ui.base.activity.BaseActivity
import com.geanbrandao.minhasdespesas.utils.RandomColors
import com.geanbrandao.minhasdespesas.utils.ZoomOutPageTransformer
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.time.OffsetDateTime
import java.util.*
import kotlin.collections.ArrayList


class StatisticsActivity : BaseActivity() {

    private val viewModel: StatisticsViewModel by viewModel()

    private var disposable: Disposable? = null

    private lateinit var binding: ActivityStatisticsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createListeners()
    }

    private fun createListeners() {
        setupToolbar()
        setupViewPager()
    }

    private fun getExpensesBetween() {
        // vai pegar os cadastrados a data atual sempre
        val startMonthDate: OffsetDateTime = OffsetDateTime.now()
        // define como 6 meses antas
        val endMonthDate: OffsetDateTime = startMonthDate.minusMonths(6).withDayOfMonth(1)
        // Primeira coisa buscar as despesas dos ultimos 6 meses
        disposable = viewModel.getExpensesBetween(this, startMonthDate, endMonthDate)
            .subscribeBy(
                onNext = {
                    generateReport(it)
                },
                onError = {
                    Timber.e(it)
                },
                onComplete = {}
            )
    }

    private fun generateReport(expenses: List<Expense>) {
        val data: ArrayList<MonthExpenseReport> = arrayListOf()
        expenses.forEach { expense ->
//            val cateforiesExpenses: ArrayList<CategoriesExpenses> = arrayListOf()
//            expense.categories.forEach { category ->
//                cateforiesExpenses.add(
//                    CategoriesExpenses(
//                        category,
//
//                    )
//                )
//            }
//
//            data.add(
//                MonthExpenseReport(
//                    expense.selectedDate,
//                    expense.selectedDate.getMonth3LettersName().plus("/").plus(expense.selectedDate.getYearNumber())
//                ))
        }
    }

    /**
     * make the initial setup for the recycler
     *
     */
    private fun setupViewPager() {

        val categoriesExpenses: ArrayList<CategoriesExpenses> = arrayListOf(
            CategoriesExpenses(
                Category(UUID.randomUUID().toString(), "Casa", "ic_house", false, false, getColorNameFromArray(0)),
                5.31f,
                2
            ),
            CategoriesExpenses(
                Category(UUID.randomUUID().toString(), "Educação", "ic_education", false, false, getColorNameFromArray(1)),
                22.38f,
                1
            ),
            CategoriesExpenses(
                Category(UUID.randomUUID().toString(), "Outros", "ic_others", false, false, getColorNameFromArray(3)),
                30.39f,
                4
            ),
            CategoriesExpenses(
                Category(UUID.randomUUID().toString(), "Supermercado", "ic_supermarket", false, false, getColorNameFromArray(7)),
                35.39f,
                2
            ),
        )

        categoriesExpenses.forEach {
            Timber.d("COLORS - ${it.category.colorName}")
        }

        val data: ArrayList<MonthExpenseReport> = arrayListOf(
            MonthExpenseReport(
                null,
                "Mar/2020",
                categoriesExpenses
            ),
            MonthExpenseReport(
                null,
                "Abr/2020",
                categoriesExpenses
            ),
            MonthExpenseReport(
                null,
                "Mai/2020",
                categoriesExpenses
            ),
        )

        val adapter = AdapterPager(this, data)
        Timber.d("DEBUG1 - adaper size - ${adapter.itemCount}")
        binding.viewPager.adapter = adapter
        binding.viewPager.setPageTransformer(ZoomOutPageTransformer())
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