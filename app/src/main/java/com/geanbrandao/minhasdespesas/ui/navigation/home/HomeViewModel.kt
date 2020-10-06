package com.geanbrandao.minhasdespesas.ui.navigation.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.geanbrandao.minhasdespesas.modal.database.expenses.ExpensesData
import com.geanbrandao.minhasdespesas.repositories.expenses.ExpensesRepository
import com.geanbrandao.minhasdespesas.repositories.expenses.ExpensesRepositoryImpl
import io.reactivex.Completable
import io.reactivex.Flowable

class HomeViewModel : ViewModel() {

    private val mRepository: ExpensesRepository = ExpensesRepositoryImpl()

    fun getAll(context: Context): Flowable<List<ExpensesData>> {
        return mRepository.getAll(context)
    }

    fun addExpense(context: Context, data: ExpensesData): Completable {
        return mRepository.addExpense(context, data)
    }

    fun deleteExpense(context: Context, data: ExpensesData): Completable {
        return mRepository.deleteExpense(context, data)
    }

    fun updateExpense(context: Context, data: ExpensesData): Completable {
        return mRepository.updateItem(context, data)
    }
}