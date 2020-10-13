package com.geanbrandao.minhasdespesas.repositories.expenses

import android.content.Context
import com.geanbrandao.minhasdespesas.mapTo
import com.geanbrandao.minhasdespesas.model.Category
import com.geanbrandao.minhasdespesas.model.Expense
import com.geanbrandao.minhasdespesas.model.database.MyDatabase
import com.geanbrandao.minhasdespesas.model.database.entity_expense_category_join.ExpenseCategoryJoinData
import com.geanbrandao.minhasdespesas.model.database.entity_expenses.ExpensesData
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.concatAll
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.Android
import timber.log.Timber

class ExpensesRepositoryImpl : ExpensesRepository {

    override fun getAll(context: Context): Flowable<Expense> {
        return MyDatabase.getDatabaseInstance(context).expensesDao().getAll()
                .switchMap {
                    getJoins(context, it)
                }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getJoins(context: Context, listExpensesData: List<ExpensesData>): Flowable<Expense> {
        val observable = listExpensesData.map { expenseData ->
            MyDatabase.getDatabaseInstance(context).expenseCategoryJoinDao().getCategoriesByExpenseId(expenseData.id)
                    .map { categoriesDate ->
                        expenseData.mapTo(categoriesDate)
                    }
        }

        return observable.concatAll()
    }

    override fun addExpense(context: Context, data: Expense): Completable {
        return MyDatabase.getDatabaseInstance(context).expensesDao().insertExpense(data.mapTo())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteExpense(context: Context, data: ExpensesData): Completable {
        return MyDatabase.getDatabaseInstance(context).expensesDao().delete(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun updateItem(context: Context, data: ExpensesData): Completable {
        return MyDatabase.getDatabaseInstance(context).expensesDao().update(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteAll(context: Context): Completable {
        return MyDatabase.getDatabaseInstance(context).expensesDao().deleteAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}