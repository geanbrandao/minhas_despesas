package com.geanbrandao.minhasdespesas.model.database.entity_expense_category_join

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.geanbrandao.minhasdespesas.model.database.entity_categories.CategoriesData
import com.geanbrandao.minhasdespesas.model.database.entity_expense_category_join.ExpenseCategoryJoinData.Companion.CATEGORY_ID
import com.geanbrandao.minhasdespesas.model.database.entity_expense_category_join.ExpenseCategoryJoinData.Companion.EXPENSE_ID
import com.geanbrandao.minhasdespesas.model.database.entity_expense_category_join.ExpenseCategoryJoinData.Companion.TABLE_NAME
import com.geanbrandao.minhasdespesas.model.database.entity_expenses.ExpensesData
import java.util.*


@Entity(
    tableName = TABLE_NAME,
    primaryKeys = [EXPENSE_ID, CATEGORY_ID],
    indices = [
        Index(value = [EXPENSE_ID]),
        Index(value = [CATEGORY_ID])
    ],
    foreignKeys = [
        ForeignKey(
            entity = ExpensesData::class,
            parentColumns = [ExpensesData.ID],
            childColumns = [EXPENSE_ID],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CategoriesData::class,
            parentColumns = [CategoriesData.ID],
            childColumns = [CATEGORY_ID],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class ExpenseCategoryJoinData(
    @ColumnInfo(name = EXPENSE_ID)
    val expenseId: String,
    @ColumnInfo(name = CATEGORY_ID)
    val categoryId: String

) {

    override fun toString(): String {
        return "$expenseId - $categoryId"
    }

    companion object {
        const val TABLE_NAME = "expense_category_join"
        const val EXPENSE_ID = "expense_id"
        const val CATEGORY_ID = "category_id"
    }
}