package com.geanbrandao.minhasdespesas.model.database.entity_expenses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.OffsetDateTime
import java.util.*

@Entity(tableName = ExpensesData.TABLE_NAME)
class ExpensesData(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = ID)
    val id: String,

    @ColumnInfo(name = AMOUNT)
    val amount: Float,

    @ColumnInfo(name = TITLE)
    val title: String,

    @ColumnInfo(name = SELECTED_DATE)
    val selectedDate: OffsetDateTime,

    @ColumnInfo(name = DESCRIPTION)
    val description: String,

    @ColumnInfo(name = CREATED_AT)
    val createdAt: OffsetDateTime,

    @ColumnInfo(name = UPDATED_AT)
    val updatedAt: OffsetDateTime

): Serializable {

    constructor(): this(UUID.randomUUID().toString(), 0f, "", OffsetDateTime.now(), "", OffsetDateTime.now(), OffsetDateTime.now())

    companion object {
        const val TABLE_NAME = "expense"
        const val ID = "id"
        const val AMOUNT = "amount"
        const val TITLE = "title"
        const val SELECTED_DATE = "selected_date"
        const val DESCRIPTION = "description"
        const val CREATED_AT = "created_at"
        const val UPDATED_AT = "updated_at"
    }

    override fun toString(): String {
        return "$id - $amount - $title - $selectedDate - $description - $createdAt - $updatedAt"
    }
}