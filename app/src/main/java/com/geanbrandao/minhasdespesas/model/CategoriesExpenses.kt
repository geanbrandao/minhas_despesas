package com.geanbrandao.minhasdespesas.model

import java.io.Serializable

class CategoriesExpenses(
    val category: Category,
    val valueSpentCategory: Float,
    val countSpentCategory: Int, // esse pode ser o mesmo de baixo
) : Serializable