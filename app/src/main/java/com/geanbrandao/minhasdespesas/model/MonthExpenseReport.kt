package com.geanbrandao.minhasdespesas.model

import java.io.Serializable
import java.time.OffsetDateTime
import java.util.*
import kotlin.collections.ArrayList

class MonthExpenseReport(
    val date: OffsetDateTime?,
    val monthTitle: String,
    val categoriesExpenses: ArrayList<CategoriesExpenses>,
) : Serializable