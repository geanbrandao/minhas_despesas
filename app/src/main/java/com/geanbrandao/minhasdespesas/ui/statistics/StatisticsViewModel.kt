package com.geanbrandao.minhasdespesas.ui.statistics

import android.content.Context
import androidx.lifecycle.ViewModel
import com.geanbrandao.minhasdespesas.model.Expense
import com.geanbrandao.minhasdespesas.repositories.expenses.ExpensesRepository
import com.geanbrandao.minhasdespesas.repositories.expenses.ExpensesRepositoryImpl
import io.reactivex.Flowable
import java.time.OffsetDateTime

class StatisticsViewModel : ViewModel() {

    private val mRepository: ExpensesRepository = ExpensesRepositoryImpl()

    fun getExpensesBetween(context: Context, startMontDate: OffsetDateTime, endMonthDate: OffsetDateTime): Flowable<List<Expense>> {
        return mRepository.getExpensesBetween(context, startMontDate, endMonthDate)
    }
}