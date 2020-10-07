package com.geanbrandao.minhasdespesas.ui.add_edit

import android.content.Context
import androidx.lifecycle.ViewModel
import com.geanbrandao.minhasdespesas.modal.database.entity_expenses.ExpensesData
import com.geanbrandao.minhasdespesas.repositories.expenses.ExpensesRepository
import com.geanbrandao.minhasdespesas.repositories.expenses.ExpensesRepositoryImpl
import io.reactivex.Completable

class AddEditViewModel: ViewModel() {
    private val mReposirtory: ExpensesRepository = ExpensesRepositoryImpl()

    fun addExpense(context: Context, data: ExpensesData): Completable {
        return mReposirtory.addExpense(context, data)
    }
}