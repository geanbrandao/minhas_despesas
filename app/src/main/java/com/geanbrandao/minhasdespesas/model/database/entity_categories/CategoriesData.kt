package com.geanbrandao.minhasdespesas.model.database.entity_categories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.geanbrandao.minhasdespesas.model.database.entity_categories.CategoriesData.Companion.TABLE_NAME
import java.util.*

@Entity(tableName = TABLE_NAME)
class CategoriesData(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = ID)
    val id: String,

    @ColumnInfo(name = NAME)
    val name: String,

    @ColumnInfo(name = ICON)
    val icon: String = "ic_tag",

    @ColumnInfo(name = CAN_REMOVE)
    val canRemove: Boolean = false,

    @ColumnInfo(name = COLOR_NAME)
    val colorName: String,
    ) {

    companion object {
        const val TABLE_NAME = "category"
        const val ID = "id"
        const val NAME = "name"
        const val ICON = "icon"
        const val CAN_REMOVE = "can_remove"
        const val COLOR_NAME = "color_name"
    }
}