package com.geanbrandao.minhasdespesas.modal.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.geanbrandao.minhasdespesas.modal.database.converters.Converters
import com.geanbrandao.minhasdespesas.modal.database.expenses.ExpensesDao
import com.geanbrandao.minhasdespesas.modal.database.expenses.ExpensesData

@Database(entities = [ExpensesData::class], version = MyDatabase.DB_VERSION, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MyDatabase: RoomDatabase() {

    abstract fun expensesDao(): ExpensesDao

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