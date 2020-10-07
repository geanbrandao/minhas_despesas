package com.geanbrandao.minhasdespesas.module

import com.geanbrandao.minhasdespesas.ui.add_edit.AddEditViewModel
import com.geanbrandao.minhasdespesas.ui.category.CategoriesViewModel
import com.geanbrandao.minhasdespesas.ui.navigation.home.HomeViewModel
import com.geanbrandao.minhasdespesas.ui.splash_screen.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel() }
    viewModel { AddEditViewModel() }
    viewModel { SplashViewModel() }
    viewModel { CategoriesViewModel() }
}