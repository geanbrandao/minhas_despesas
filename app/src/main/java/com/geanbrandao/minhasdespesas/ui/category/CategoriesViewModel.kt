package com.geanbrandao.minhasdespesas.ui.category

import android.content.Context
import androidx.lifecycle.ViewModel
import com.geanbrandao.minhasdespesas.modal.database.entity_categories.CategoriesData
import com.geanbrandao.minhasdespesas.repositories.categories.CategoriesRepository
import com.geanbrandao.minhasdespesas.repositories.categories.CategoriesRepositoryImpl
import io.reactivex.Completable
import io.reactivex.Flowable

class CategoriesViewModel : ViewModel() {
    private val mRepository: CategoriesRepository = CategoriesRepositoryImpl()

    fun insert(context: Context, data: CategoriesData): Completable {
        return mRepository.insert(context, data)
    }

    fun insertAll(context: Context, datas: List<CategoriesData>): Completable {
        return mRepository.insertAll(context, datas)
    }

    fun getAll(context: Context): Flowable<List<CategoriesData>> {
        return mRepository.getAll(context)
    }

    fun delete(context: Context, data: CategoriesData): Completable {
        return mRepository.delete(context, data)
    }

    fun update(context: Context, data: CategoriesData): Completable {
        return mRepository.update(context, data)
    }
}