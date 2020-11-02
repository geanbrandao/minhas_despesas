package com.geanbrandao.minhasdespesas.model.database.entity_expenses

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.time.OffsetDateTime

@Dao
interface ExpensesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExpense(data: ExpensesData): Completable

    @Query("SELECT * FROM ${ExpensesData.TABLE_NAME} ORDER BY ${ExpensesData.SELECTED_DATE} DESC")
    fun getAll(): Flowable<List<ExpensesData>>

    @Query("SELECT * FROM ${ExpensesData.TABLE_NAME} ORDER BY ${ExpensesData.SELECTED_DATE} DESC")
    fun getAllSingle(): Single<List<ExpensesData>>

    @Query("SELECT * FROM ${ExpensesData.TABLE_NAME} WHERE ${ExpensesData.SELECTED_DATE} BETWEEN :startMontDate AND :endMonthDate ORDER BY ${ExpensesData.SELECTED_DATE} DESC")
    fun getExpensesBetween(startMontDate: OffsetDateTime, endMonthDate: OffsetDateTime): Flowable<List<ExpensesData>>

    @Delete
    fun delete(data: ExpensesData): Completable

    @Query("DELETE FROM ${ExpensesData.TABLE_NAME}")
    fun deleteAll(): Completable

    @Update
    fun update(data: ExpensesData): Completable
}