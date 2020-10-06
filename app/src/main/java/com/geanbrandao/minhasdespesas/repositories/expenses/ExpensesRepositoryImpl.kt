package com.geanbrandao.minhasdespesas.repositories.expenses

import android.content.Context
import com.geanbrandao.minhasdespesas.modal.database.MyDatabase
import com.geanbrandao.minhasdespesas.modal.database.expenses.ExpensesData
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.Android

class ExpensesRepositoryImpl: ExpensesRepository {

    override fun getAll(context: Context): Flowable<List<ExpensesData>> {
        return MyDatabase.getDatabaseInstance(context).expensesDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun addExpense(context: Context, data: ExpensesData): Completable {
        return MyDatabase.getDatabaseInstance(context).expensesDao().insertExpense(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteExpense(context: Context, data: ExpensesData): Completable {
        return MyDatabase.getDatabaseInstance(context).expensesDao().delete(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun clearDatabase(context: Context) {

    }
}