package com.geanbrandao.minhasdespesas.ui.splash_screen

import android.os.Bundle
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.goToActivity
import com.geanbrandao.minhasdespesas.model.database.entity_categories.CategoriesData
import com.geanbrandao.minhasdespesas.ui.base.activity.BaseActivity
import com.geanbrandao.minhasdespesas.ui.home.HomeActivity
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.*

class SplashScreenActivity : BaseActivity() {

    private val viewModel: SplashViewModel by viewModel()

    private var disposableGet: Disposable? = null
    private var disposableAdd: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        createListeners()
    }

    private fun createListeners() {
        setupCategories()
    }

    private fun setupCategories() {
        disposableGet = viewModel.getAll(this).subscribeBy(
                onNext = {
                    if (it.isEmpty()) {
                        createCategories()
                    } else {
                        disposableGet?.dispose()
                        goToActivity(HomeActivity::class.java)
                        finish()
                    }
                },
                onError = {
                    Timber.e(it)
                },
                onComplete = {}
        )
    }
    // TODO eu nao posso limpar a database. Agora tem outra tabela

    private fun createCategories() {
        val list: List<CategoriesData> = listOf(
                CategoriesData(UUID.randomUUID().toString(), "Casa", "ic_house", false),
                CategoriesData(UUID.randomUUID().toString(), "Educação", "ic_education", false),
                CategoriesData(UUID.randomUUID().toString(), "Eletrônicos", "ic_computer", false),
                CategoriesData(UUID.randomUUID().toString(), "Outros", "ic_others", false),
                CategoriesData(UUID.randomUUID().toString(), "Restaurante", "ic_restaurant", false),
                CategoriesData(UUID.randomUUID().toString(), "Saúde", "ic_healing", false),
                CategoriesData(UUID.randomUUID().toString(), "Serviços", "ic_service", false),
                CategoriesData(UUID.randomUUID().toString(), "Supermercado", "ic_supermarket", false),
                CategoriesData(UUID.randomUUID().toString(), "Transporte", "ic_bus", false),
                CategoriesData(UUID.randomUUID().toString(), "Vestuário", "ic_store", false),
                CategoriesData(UUID.randomUUID().toString(), "Viagem", "ic_bus", false))

        disposableAdd = viewModel.insertAll(this, list).subscribeBy(
                onError = {
                    Timber.e(it)
                },
                onComplete = {
                    goToActivity(HomeActivity::class.java)
                    finish()
                }
        )
    }

    override fun onStop() {
        super.onStop()
        disposableAdd?.dispose()
        disposableGet?.dispose()
    }

    override fun onBackPressed() {

    }
}