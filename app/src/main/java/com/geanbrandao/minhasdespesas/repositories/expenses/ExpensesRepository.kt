package com.geanbrandao.minhasdespesas.repositories.expenses

import android.content.Context
import com.geanbrandao.minhasdespesas.model.Expense
import com.geanbrandao.minhasdespesas.model.database.entity_expenses.ExpensesData
import io.reactivex.Completable
import io.reactivex.Flowable

interface ExpensesRepository {
    fun getAll(context: Context): Flowable<Expense>
    fun addExpense(context: Context, data: Expense): Completable
    fun deleteExpense(context: Context, data: ExpensesData): Completable
    fun updateItem(context: Context, data: ExpensesData): Completable
    fun deleteAll(context: Context): Completable
}