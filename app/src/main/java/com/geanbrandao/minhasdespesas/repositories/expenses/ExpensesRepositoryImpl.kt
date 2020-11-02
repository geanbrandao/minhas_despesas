package com.geanbrandao.minhasdespesas.repositories.expenses

import android.content.Context
import com.geanbrandao.minhasdespesas.combineLatest
import com.geanbrandao.minhasdespesas.mapTo
import com.geanbrandao.minhasdespesas.model.Expense
import com.geanbrandao.minhasdespesas.model.MonthExpenseReport
import com.geanbrandao.minhasdespesas.model.database.MyDatabase
import com.geanbrandao.minhasdespesas.model.database.entity_expense_category_join.ExpenseCategoryJoinData
import com.geanbrandao.minhasdespesas.model.database.entity_expenses.ExpensesData
import com.geanbrandao.minhasdespesas.zip
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.time.OffsetDateTime


class ExpensesRepositoryImpl : ExpensesRepository {

    override fun getExpensesBetween(context: Context, startMontDate: OffsetDateTime, endMonthDate: OffsetDateTime): Flowable<List<Expense>> {
        return MyDatabase.getDatabaseInstance(context)
            .expensesDao().getExpensesBetween(startMontDate, endMonthDate)
            .switchMap {
                makeExpenseFlowable(context, it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    override fun getAll(context: Context): Flowable<List<Expense>> {
        return MyDatabase.getDatabaseInstance(context).expensesDao().getAll()
                .switchMap {
                    makeExpenseFlowable(context, it)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    }

    private fun makeExpenseFlowable(context: Context, list: List<ExpensesData>): Flowable<List<Expense>> {
        if(list.isEmpty()) {
            return Flowable.just(emptyList())
        }

        val flowables = list.map { expensesData ->
            MyDatabase.getDatabaseInstance(context).expenseCategoryJoinDao()
                    .flowableByExpenseId(expensesData.id)
                    .map { categoriesData -> expensesData.mapTo(categoriesData) }
        }

        return flowables.combineLatest()
    }


//    fun getAll2(context: Context): Single<List<Expense>> {
//        return MyDatabase.getDatabaseInstance(context).expensesDao().getAll()
//                .flatMap {
//                    makeExpensesSingle(context, it)
//                }
//    }
//
//    fun makeExpensesSingle(context: Context, expensesData: List<ExpensesData>): Single<List<Expense>> {
//        if (expensesData.isEmpty()) {
//            return Single.just(emptyList())
//        }
//
//        val singles = expensesData.map { ed ->
//            MyDatabase.getDatabaseInstance(context).expenseCategoryJoinDao()
//                    .getCategoriesByExpenseId(ed.id)
//                    .map { categoriesData -> ed.mapTo(categoriesData) }
//        }
//
//        return singles.zip()
//    }

    override fun addExpense(context: Context, data: Expense): Completable {

        val completableA = MyDatabase.getDatabaseInstance(context).expensesDao()
                .insertExpense(data.mapTo())

        val joins = data.categories.map {
            ExpenseCategoryJoinData(data.id, it.id)
        }

        val completableB = MyDatabase.getDatabaseInstance(context).expenseCategoryJoinDao()
                .insert(joins)

        return completableA.andThen(completableB)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteExpense(context: Context, data: ExpensesData): Completable {
        return MyDatabase.getDatabaseInstance(context).expensesDao().delete(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun updateItem(
            context: Context, data: ExpensesData,
            joinsRemove: List<ExpenseCategoryJoinData>,
            joinsAdd: List<ExpenseCategoryJoinData>
    ): Completable {

        val completableA = MyDatabase.getDatabaseInstance(context).expensesDao()
                .update(data)
        val completableB = MyDatabase.getDatabaseInstance(context).expenseCategoryJoinDao()
                .delete(joinsRemove)
        val completableC = MyDatabase.getDatabaseInstance(context).expenseCategoryJoinDao()
                .insert(joinsAdd)

        return completableA.andThen(completableB).andThen(completableC)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteAll(context: Context): Completable {
        return MyDatabase.getDatabaseInstance(context).expensesDao().deleteAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}