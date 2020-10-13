package com.geanbrandao.minhasdespesas.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.geanbrandao.minhasdespesas.model.database.converters.Converters
import com.geanbrandao.minhasdespesas.model.database.entity_categories.CategoriesDao
import com.geanbrandao.minhasdespesas.model.database.entity_categories.CategoriesData
import com.geanbrandao.minhasdespesas.model.database.entity_expense_category_join.ExpenseCategoryJoinDao
import com.geanbrandao.minhasdespesas.model.database.entity_expense_category_join.ExpenseCategoryJoinData
import com.geanbrandao.minhasdespesas.model.database.entity_expenses.ExpensesDao
import com.geanbrandao.minhasdespesas.model.database.entity_expenses.ExpensesData

@Database(
    entities = [
        ExpensesData::class,
        CategoriesData::class,
        ExpenseCategoryJoinData::class
    ],
    version = MyDatabase.DB_VERSION,
    exportSchema = false)
@TypeConverters(Converters::class)
abstract class MyDatabase: RoomDatabase() {

    abstract fun expensesDao(): ExpensesDao

    abstract fun categoriesDao(): CategoriesDao

    abstract fun expenseCategoryJoinDao(): ExpenseCategoryJoinDao

    companion object {
        @Volatile
        private var databaseInstance: MyDatabase? = null

        fun getDatabaseInstance(context: Context): MyDatabase =
            databaseInstance ?: synchronized(this) {
                databaseInstance ?: buildDatabaseInstance(context).also {
                    databaseInstance = it
                }
            }


        private fun buildDatabaseInstance(mContext: Context) =
            Room.databaseBuilder(mContext, MyDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()

        const val DB_VERSION = 1
        const val DB_NAME = "MinhasDespesas.db"
    }
}