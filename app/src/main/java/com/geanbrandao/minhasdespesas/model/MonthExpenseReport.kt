package com.geanbrandao.minhasdespesas.model

import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class MonthExpenseReport(
    val date: Date?,
    val monthTitle: String,
    val categoriesExpenses: ArrayList<CategoriesExpenses>,
) : Serializable