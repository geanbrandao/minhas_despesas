package com.geanbrandao.minhasdespesas.repositories.expense_category_join

import android.content.Context
import com.geanbrandao.minhasdespesas.model.database.MyDatabase
import com.geanbrandao.minhasdespesas.model.database.entity_categories.CategoriesData
import com.geanbrandao.minhasdespesas.model.database.entity_expense_category_join.ExpenseCategoryJoinData
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ExpenseCategoryJoinRepositoryImpl: ExpenseCategoryJoinRepository {

    override fun insert(context: Context, join: ExpenseCategoryJoinData): Completable {
        return MyDatabase.getDatabaseInstance(context).expenseCategoryJoinDao().insert(join)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun insert(context: Context, joins: List<ExpenseCategoryJoinData>): Completable {
        return MyDatabase.getDatabaseInstance(context).expenseCategoryJoinDao().insert(joins)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getCategoriesByExpenseId(context: Context, expenseId: String): Single<List<CategoriesData>> {
        return MyDatabase.getDatabaseInstance(context).expenseCategoryJoinDao()
                .getCategoriesByExpenseId(expenseId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun delete(context: Context, joins: List<ExpenseCategoryJoinData>): Completable {
        return MyDatabase.getDatabaseInstance(context).expenseCategoryJoinDao().delete(joins)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}