package com.geanbrandao.minhasdespesas.module

import com.geanbrandao.minhasdespesas.ui.navigation.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel() }
}