package com.geanbrandao.minhasdespesas.ui.navigation.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.geanbrandao.minhasdespesas.modal.database.expenses.ExpensesData
import com.geanbrandao.minhasdespesas.repositories.expenses.ExpensesRepository
import com.geanbrandao.minhasdespesas.repositories.expenses.ExpensesRepositoryImpl
import io.reactivex.Flowable

class HomeViewModel : ViewModel() {

    private val mRepository: ExpensesRepository = ExpensesRepositoryImpl()

    fun getAll(context: Context): Flowable<List<ExpensesData>> {
        return mRepository.getAll(context)
    }
}