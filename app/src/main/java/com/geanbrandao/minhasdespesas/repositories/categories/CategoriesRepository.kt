package com.geanbrandao.minhasdespesas.repositories.categories

import android.content.Context
import com.geanbrandao.minhasdespesas.modal.database.entity_categories.CategoriesData
import io.reactivex.Completable
import io.reactivex.Flowable

interface CategoriesRepository {
    fun insert(context: Context, data: CategoriesData): Completable
    fun insertAll(context: Context, datas: List<CategoriesData>): Completable
    fun getAll(context: Context): Flowable<List<CategoriesData>>
    fun delete(context: Context, data: CategoriesData): Completable
    fun update(context: Context, data: CategoriesData): Completable
}