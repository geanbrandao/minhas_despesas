package com.geanbrandao.minhasdespesas.module

import com.geanbrandao.minhasdespesas.ui.add_edit.AddEditViewModel
import com.geanbrandao.minhasdespesas.ui.category.CategoriesViewModel
import com.geanbrandao.minhasdespesas.ui.navigation.home.HomeViewModel
import com.geanbrandao.minhasdespesas.ui.navigation.settings.SettingsViewModel
import com.geanbrandao.minhasdespesas.ui.splash_screen.SplashViewModel
import com.geanbrandao.minhasdespesas.ui.statistics.StatisticsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel() }
    viewModel { AddEditViewModel() }
    viewModel { SplashViewModel() }
    viewModel { CategoriesViewModel() }
    viewModel { SettingsViewModel() }
    viewModel { StatisticsViewModel() }
}