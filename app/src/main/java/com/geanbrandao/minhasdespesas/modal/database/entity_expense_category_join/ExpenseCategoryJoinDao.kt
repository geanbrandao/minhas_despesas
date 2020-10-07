package com.geanbrandao.minhasdespesas.modal.database.entity_expense_category_join

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.geanbrandao.minhasdespesas.modal.database.entity_categories.CategoriesData
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ExpenseCategoryJoinDao {

    @Insert
    fun insert(join: ExpenseCategoryJoinData): Completable

    @Query(
        """
            SELECT * FROM ${CategoriesData.TABLE_NAME} INNER JOIN ${ExpenseCategoryJoinData.TABLE_NAME}
            ON ${CategoriesData.TABLE_NAME}.${CategoriesData.ID} = ${ExpenseCategoryJoinData.TABLE_NAME}.${ExpenseCategoryJoinData.CATEGORY_ID}
            WHERE ${ExpenseCategoryJoinData.TABLE_NAME}.${ExpenseCategoryJoinData.EXPENSE_ID} = :expenseId
        """
    )
    fun getCategoriesByExpenseId(expenseId: Long): Single<List<CategoriesData>>
}