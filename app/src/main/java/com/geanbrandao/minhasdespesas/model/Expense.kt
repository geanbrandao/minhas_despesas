package com.geanbrandao.minhasdespesas.model

import java.io.Serializable
import java.time.OffsetDateTime
import kotlin.collections.ArrayList

class Expense(
        val id: String,
        val amount: Float,
        val title: String,
        val selectedDate: OffsetDateTime, // passa o valor selecionado
        val description: String,
        val categories: ArrayList<Category>,
        val createdAt: OffsetDateTime = OffsetDateTime.now(),
        val updatedAt: OffsetDateTime = OffsetDateTime.now()
) : Serializable {
    override fun toString(): String {
        return "$id - $amount $title - $selectedDate - $selectedDate - $description ${categories.toString()}"
    }

    constructor() : this("", 0f, "", OffsetDateTime.now(), "", arrayListOf(), OffsetDateTime.now(), OffsetDateTime.now())
}