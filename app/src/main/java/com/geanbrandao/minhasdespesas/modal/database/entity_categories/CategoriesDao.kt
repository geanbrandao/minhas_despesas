package com.geanbrandao.minhasdespesas.modal.database.entity_categories

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface CategoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: CategoriesData): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(datas: List<CategoriesData>): Completable

    @Query("SELECT * FROM ${CategoriesData.TABLE_NAME}")
    fun getAll(): Flowable<List<CategoriesData>>

    @Delete
    fun delete(data: CategoriesData): Completable

    @Update
    fun update(data: CategoriesData): Completable
}