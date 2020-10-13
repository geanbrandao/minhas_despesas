package com.geanbrandao.minhasdespesas.repositories.expense_category_join

import android.content.Context
import com.geanbrandao.minhasdespesas.model.database.entity_categories.CategoriesData
import com.geanbrandao.minhasdespesas.model.database.entity_expense_category_join.ExpenseCategoryJoinData
import io.reactivex.Completable
import io.reactivex.Single

interface ExpenseCategoryJoinRepository {
    fun insert(context: Context, join: ExpenseCategoryJoinData): Completable
    fun insert(context: Context, joins: List<ExpenseCategoryJoinData>): Completable
    fun getCategoriesByExpenseId(context: Context, expenseId: String): Single<List<CategoriesData>>
}