package com.geanbrandao.minhasdespesas.model

import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class Expense(
    val id: String,
    val amount: Float,
    val title: String,
    val date: String,
    val description: String,
    val categories: ArrayList<Category>
) : Serializable {
    override fun toString(): String {
        return "$id - $amount $title - $date - $date - $description ${categories.toString()}"
    }

    constructor(): this("", 0f, "", "", "", arrayListOf())
}