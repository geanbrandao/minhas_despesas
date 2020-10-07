package com.geanbrandao.minhasdespesas.modal.database.entity_categories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.modal.database.entity_categories.CategoriesData.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class CategoriesData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Long,

    @ColumnInfo(name = NAME)
    val name: String,

    @ColumnInfo(name = ICON)
    val icon: Int = R.drawable.ic_tag,

    @ColumnInfo(name = CAN_REMOVE)
    val canRemove: Boolean = false
    ) {

    companion object {
        const val TABLE_NAME = "category"
        const val ID = "id"
        const val NAME = "name"
        const val ICON = "icon"
        const val CAN_REMOVE = "can_remove"
    }
}