package com.geanbrandao.minhasdespesas.ui.splash_screen

import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import com.geanbrandao.minhasdespesas.databinding.ActivitySplashScreenBinding
import com.geanbrandao.minhasdespesas.getColorNameFromArray
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

    private lateinit var binding: ActivitySplashScreenBinding

    private val viewModel: SplashViewModel by viewModel()

    private var disposableGet: Disposable? = null
    private var disposableAdd: Disposable? = null

    private var dbOk = false
    private var motionOK = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        createListeners()
    }

    private fun createListeners() {
        binding.motionBase.setTransitionListener(object: MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                motionOK = true
                nextScreen()
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}

        })


        setupCategories()
    }

    private fun setupCategories() {
        disposableGet = viewModel.getAll(this).subscribeBy(
                onNext = {
                    if (it.isEmpty()) {
                        createCategories()
                    } else {
                        disposableGet?.dispose()
                        dbOk = true
                        nextScreen()
                    }
                },
                onError = {
                    Timber.e(it)
                },
                onComplete = {}
        )
    }
    // TODO eu nao posso limpar a database. Agora tem outra tabela
    // TODO fazer processo da array ao adicionar uma categoria
    private fun createCategories() {
        val list: List<CategoriesData> = listOf(
                CategoriesData(UUID.randomUUID().toString(), "Casa", "ic_house", false, getColorNameFromArray(0)),
                CategoriesData(UUID.randomUUID().toString(), "Educação", "ic_education", false, getColorNameFromArray(1)),
                CategoriesData(UUID.randomUUID().toString(), "Eletrônicos", "ic_computer", false, getColorNameFromArray(2)),
                CategoriesData(UUID.randomUUID().toString(), "Outros", "ic_others", false, getColorNameFromArray(3)),
                CategoriesData(UUID.randomUUID().toString(), "Restaurante", "ic_restaurant", false, getColorNameFromArray(4)),
                CategoriesData(UUID.randomUUID().toString(), "Saúde", "ic_healing", false, getColorNameFromArray(5)),
                CategoriesData(UUID.randomUUID().toString(), "Serviços", "ic_service", false, getColorNameFromArray(6)),
                CategoriesData(UUID.randomUUID().toString(), "Supermercado", "ic_supermarket", false, getColorNameFromArray(7)),
                CategoriesData(UUID.randomUUID().toString(), "Transporte", "ic_bus", false, getColorNameFromArray(8)),
                CategoriesData(UUID.randomUUID().toString(), "Vestuário", "ic_store", false, getColorNameFromArray(9)),
                CategoriesData(UUID.randomUUID().toString(), "Viagem", "ic_bus", false, getColorNameFromArray(10)))

        disposableAdd = viewModel.insertAll(this, list).subscribeBy(
                onError = {
                    Timber.e(it)
                },
                onComplete = {
                    dbOk = true
                    nextScreen()
                }
        )
    }

    private fun nextScreen() {
        if (dbOk && motionOK) {
            goToActivity(HomeActivity::class.java)
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        disposableAdd?.dispose()
        disposableGet?.dispose()
    }

    override fun onBackPressed() {

    }
}