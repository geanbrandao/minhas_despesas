package com.geanbrandao.minhasdespesas.repositories.expenses

import android.content.Context
import com.geanbrandao.minhasdespesas.modal.database.expenses.ExpensesData
import io.reactivex.Completable
import io.reactivex.Flowable

interface ExpensesRepository {
    fun getAll(context: Context): Flowable<List<ExpensesData>>
    fun addExpense(context: Context, data: ExpensesData): Completable
    fun deleteExpense(context: Context, data: ExpensesData): Completable
    fun updateItem(context: Context, data: ExpensesData): Completable
}