package com.geanbrandao.minhasdespesas.modal.database.expenses

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface ExpensesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExpense(data: ExpensesData): Completable

    @Query("SELECT * FROM ${ExpensesData.TABLE_NAME}")
    fun getAll(): Flowable<List<ExpensesData>>

    @Delete
    fun delete(data: ExpensesData): Completable

    @Update
    fun update(data: ExpensesData): Completable
}