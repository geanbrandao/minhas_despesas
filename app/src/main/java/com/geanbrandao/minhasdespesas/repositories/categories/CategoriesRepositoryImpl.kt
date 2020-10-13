package com.geanbrandao.minhasdespesas.repositories.categories

import android.content.Context
import com.geanbrandao.minhasdespesas.model.database.MyDatabase
import com.geanbrandao.minhasdespesas.model.database.entity_categories.CategoriesData
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CategoriesRepositoryImpl : CategoriesRepository {

    override fun insert(context: Context, data: CategoriesData): Completable {
        return MyDatabase.getDatabaseInstance(context).categoriesDao().insert(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun insertAll(context: Context, datas: List<CategoriesData>): Completable {
        return MyDatabase.getDatabaseInstance(context).categoriesDao().insertAll(datas)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getAll(context: Context): Flowable<List<CategoriesData>> {
        return MyDatabase.getDatabaseInstance(context).categoriesDao().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun delete(context: Context, data: CategoriesData): Completable {
        return MyDatabase.getDatabaseInstance(context).categoriesDao().delete(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun update(context: Context, data: CategoriesData): Completable {
        return MyDatabase.getDatabaseInstance(context).categoriesDao().update(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}