package com.geanbrandao.minhasdespesas.ui.navigation.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import com.geanbrandao.minhasdespesas.repositories.expenses.ExpensesRepository
import com.geanbrandao.minhasdespesas.repositories.expenses.ExpensesRepositoryImpl
import io.reactivex.Completable

class SettingsViewModel: ViewModel() {
    private val mRepository: ExpensesRepository = ExpensesRepositoryImpl()

    fun deleteAll(context: Context): Completable {
        return mRepository.deleteAll(context)
    }
}