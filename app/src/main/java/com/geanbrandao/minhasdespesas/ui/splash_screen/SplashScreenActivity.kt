package com.geanbrandao.minhasdespesas.ui.splash_screen

import android.os.Bundle
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.goToActivity
import com.geanbrandao.minhasdespesas.modal.database.entity_categories.CategoriesData
import com.geanbrandao.minhasdespesas.ui.base.activity.BaseActivity
import com.geanbrandao.minhasdespesas.ui.home.HomeActivity
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

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
                CategoriesData(0, "Casa", R.drawable.ic_house, false),
                CategoriesData(0, "Educação", R.drawable.ic_education, false),
                CategoriesData(0, "Eletrônicos", R.drawable.ic_computer, false),
                CategoriesData(0, "Outros", R.drawable.ic_others, false),
                CategoriesData(0, "Restaurante", R.drawable.ic_restaurant, false),
                CategoriesData(0, "Saúde", R.drawable.ic_healing, false),
                CategoriesData(0, "Serviços", R.drawable.ic_service, false),
                CategoriesData(0, "Supermercado", R.drawable.ic_supermarket, false),
                CategoriesData(0, "Transporte", R.drawable.ic_bus, false),
                CategoriesData(0, "Vestuário", R.drawable.ic_store, false),
                CategoriesData(0, "Viagem", R.drawable.ic_bus, false))

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