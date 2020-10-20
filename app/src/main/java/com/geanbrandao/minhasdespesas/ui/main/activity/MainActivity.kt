package com.geanbrandao.minhasdespesas.ui.main.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.filterById
import com.geanbrandao.minhasdespesas.goToActivity
import com.geanbrandao.minhasdespesas.model.Category
import com.geanbrandao.minhasdespesas.ui.splash_screen.SplashScreenActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goToActivity(SplashScreenActivity::class.java)
        finish()


        progressbar.max = 250
        progressbar.progress = 99

        /*
        val lastCategory = listOf(
                Category(
                        "7cdc58c0-e25f-43fa-89a4-3e6904304bf7",
                        "Restaurante",
                        "ic_restaurant",
                        false,
                        false),
                Category(
                        "a9fd5e71-593d-4ba9-9b4b-b9d613289690",
                        "Serviços",
                        "ic_service",
                        false,
                        false),
                Category(
                        "b182a73e-0e64-4ccf-9a06-ce4a42f0b9b0",
                        "ic_education",
                        "ic_restaurant",
                        false,
                        false))

        val newCategory = listOf(
                Category(
                        "7cdc58c0-e25f-43fa-89a4-3e6904304bf",
                        "Restaurante",
                        "ic_restaurant",
                        false,
                        false),
                Category(
                        "a9fd5e71-593d-4ba9-9b4b-b9d613289690",
                        "Serviços",
                        "ic_service",
                        false,
                        false),
                Category(
                        "b182a73e-0e64-4ccf-9a06-ce4a42f0b9b0",
                        "ic_education",
                        "ic_restaurant",
                        false,
                        false))

//        lastCategory.minus(newCategory)
//        newCategory.minus(lastCategory)
//
//        val addCategory = lastCategory.filterNot {
//            val list = newCategory.map { it.id }
//            it.id in list
//        }
//
//        val removeCategory = newCategory.filterNot {
//            val list = lastCategory.map { it.id }
//            it.id in list
//        }

        val removeCategory = lastCategory.filterById(newCategory)

        val addCategory = removeCategory.filterById(lastCategory)


        Timber.d("ADICIONA ESSES $addCategory")
        Timber.d("REMOVE ESSES $removeCategory")

         */
    }
}

/*

[7cdc58c0-e25f-43fa-89a4-3e6904304bf7 - Restaurante - ic_restaurant - false - false,
a9fd5e71-593d-4ba9-9b4b-b9d613289690 - Serviços - ic_service - false - false,
b182a73e-0e64-4ccf-9a06-ce4a42f0b9b0 - Educação - ic_education - false - false]
[7cdc58c0-e25f-43fa-89a4-3e6904304bf7 - Restaurante - ic_restaurant - false - false, a9fd5e71-593d-4ba9-9b4b-b9d613289690 - Serviços - ic_service - false - false, b182a73e-0e64-4ccf-9a06-ce4a42f0b9b0 - Educação - ic_education - false - false]

 */