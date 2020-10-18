package com.geanbrandao.minhasdespesas.ui.navigation.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.geanbrandao.minhasdespesas.mapTo
import com.geanbrandao.minhasdespesas.model.Expense
import com.geanbrandao.minhasdespesas.model.database.entity_expense_category_join.ExpenseCategoryJoinData
import com.geanbrandao.minhasdespesas.model.database.entity_expenses.ExpensesData
import com.geanbrandao.minhasdespesas.repositories.expense_category_join.ExpenseCategoryJoinRepository
import com.geanbrandao.minhasdespesas.repositories.expense_category_join.ExpenseCategoryJoinRepositoryImpl
import com.geanbrandao.minhasdespesas.repositories.expenses.ExpensesRepository
import com.geanbrandao.minhasdespesas.repositories.expenses.ExpensesRepositoryImpl
import io.reactivex.Completable
import io.reactivex.Flowable

class HomeViewModel : ViewModel() {

    private val mRepository: ExpensesRepository = ExpensesRepositoryImpl()
    private val mRepositoryCategory: ExpenseCategoryJoinRepository = ExpenseCategoryJoinRepositoryImpl()

    fun getAll(context: Context): Flowable<List<Expense>> {
        return mRepository.getAll(context)
    }

    fun addExpense(context: Context, data: Expense): Completable {
        return mRepository.addExpense(context, data)
    }

    fun deleteExpense(context: Context, data: Expense): Completable {
        return mRepository.deleteExpense(context, data.mapTo())
    }

    fun updateExpense(
            context: Context, data: Expense,
            joinsRemove: List<ExpenseCategoryJoinData>,
            joinsAdd: List<ExpenseCategoryJoinData>
    ): Completable {
        return mRepository.updateItem(context, data.mapTo(), joinsRemove, joinsAdd)
    }

    fun insertExpenseCategoryJoin(context: Context, joins: List<ExpenseCategoryJoinData>): Completable {
        return mRepositoryCategory.insert(context, joins)
    }

    fun removeExpenseCategoryJoin(context: Context, joins: List<ExpenseCategoryJoinData>): Completable {
        return mRepositoryCategory.delete(context, joins)
    }
}