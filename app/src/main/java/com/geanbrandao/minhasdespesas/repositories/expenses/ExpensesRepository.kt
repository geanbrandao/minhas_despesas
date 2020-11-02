package com.geanbrandao.minhasdespesas.repositories.expenses

import android.content.Context
import com.geanbrandao.minhasdespesas.model.Expense
import com.geanbrandao.minhasdespesas.model.database.entity_expense_category_join.ExpenseCategoryJoinData
import com.geanbrandao.minhasdespesas.model.database.entity_expenses.ExpensesData
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.time.OffsetDateTime

interface ExpensesRepository {
    fun getAll(context: Context): Flowable<List<Expense>>
    fun addExpense(context: Context, data: Expense): Completable
    fun deleteExpense(context: Context, data: ExpensesData): Completable
    fun updateItem(context: Context, data: ExpensesData, joinsRemove: List<ExpenseCategoryJoinData>, joinsAdd: List<ExpenseCategoryJoinData>): Completable
    fun deleteAll(context: Context): Completable
    fun getExpensesBetween(context: Context, startMontDate: OffsetDateTime, endMonthDate: OffsetDateTime): Flowable<List<Expense>>
}