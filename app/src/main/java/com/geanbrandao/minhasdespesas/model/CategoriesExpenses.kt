package com.geanbrandao.minhasdespesas.model

import java.io.Serializable

class CategoriesExpenses(
    val category: Category,
    val valueSpentCategory: Float, // valor gasto nesta categoria durante o mes
    val countSpentCategory: Int, // quantidade de gastos na categoria toda mes
) : Serializable