package com.geanbrandao.minhasdespesas.repositories.expenses

import android.content.Context
import com.geanbrandao.minhasdespesas.modal.database.expenses.ExpensesData
import io.reactivex.Flowable

interface ExpensesRepository {
    fun getAll(context: Context): Flowable<List<ExpensesData>>
}