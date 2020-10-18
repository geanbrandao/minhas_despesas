package com.geanbrandao.minhasdespesas.model.database.entity_expense_category_join

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.geanbrandao.minhasdespesas.model.database.entity_categories.CategoriesData
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*

@Dao
interface ExpenseCategoryJoinDao {

    @Insert
    fun insert(join: ExpenseCategoryJoinData): Completable

    @Insert
    fun insert(joins: List<ExpenseCategoryJoinData>): Completable

    @Delete
    fun delete(joins: List<ExpenseCategoryJoinData>): Completable

    @Query(
        """
            SELECT * FROM ${CategoriesData.TABLE_NAME} INNER JOIN ${ExpenseCategoryJoinData.TABLE_NAME}
            ON ${CategoriesData.TABLE_NAME}.${CategoriesData.ID} = ${ExpenseCategoryJoinData.TABLE_NAME}.${ExpenseCategoryJoinData.CATEGORY_ID}
            WHERE ${ExpenseCategoryJoinData.TABLE_NAME}.${ExpenseCategoryJoinData.EXPENSE_ID} = :expenseId
        """
    )
    fun getCategoriesByExpenseId(expenseId: String): Single<List<CategoriesData>>

    @Query(
        """
            SELECT * FROM ${CategoriesData.TABLE_NAME} INNER JOIN ${ExpenseCategoryJoinData.TABLE_NAME}
            ON ${CategoriesData.TABLE_NAME}.${CategoriesData.ID} = ${ExpenseCategoryJoinData.TABLE_NAME}.${ExpenseCategoryJoinData.CATEGORY_ID}
            WHERE ${ExpenseCategoryJoinData.TABLE_NAME}.${ExpenseCategoryJoinData.EXPENSE_ID} = :expenseId
        """
    )
    fun flowableByExpenseId(expenseId: String): Flowable<List<CategoriesData>>
}