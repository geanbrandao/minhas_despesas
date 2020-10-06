package com.geanbrandao.minhasdespesas.modal.database.expenses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = ExpensesData.TABLE_NAME)
class ExpensesData(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Long,

    @ColumnInfo(name = AMOUNT)
    val amount: Float,

    @ColumnInfo(name = TITLE)
    val title: String,

    @ColumnInfo(name = DATE)
    val date: Date,

    @ColumnInfo(name = DESCRIPTION)
    val description: String

): Serializable {

    constructor(): this(0, 0f, "", Date(), "")

    companion object {
        const val TABLE_NAME = "expense"
        const val ID = "expense_id"
        const val AMOUNT = "amount"
        const val TITLE = "title"
        const val TAGS = "tags"
        const val DATE = "date"
        const val DESCRIPTION = "description"
    }

    override fun toString(): String {
        return "$id - $amount - $title - $date - $description"
    }
}