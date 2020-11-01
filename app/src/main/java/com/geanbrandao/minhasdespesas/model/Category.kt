package com.geanbrandao.minhasdespesas.model

import java.io.Serializable

class Category(
    val id: String,
    val name: String,
    val icon: String,
    val canRemove: Boolean,
    var isSelected: Boolean = false,
    var colorName: String
) : Serializable {

    override fun toString(): String {
        return "$id - $name - $icon - $canRemove - $isSelected - $colorName"
    }
}