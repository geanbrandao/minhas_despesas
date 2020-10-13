package com.geanbrandao.minhasdespesas.model.database.converters

import androidx.room.TypeConverter
import com.geanbrandao.minhasdespesas.model.database.entity_expenses.ExpensesData
import com.google.gson.GsonBuilder
import java.util.*
import kotlin.collections.ArrayList

class Converters {
    // TIMESTAMP
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun arrayToString(value: ArrayList<ExpensesData>?): String? {
        value?.let {
            return GsonBuilder().create().toJson(it)
        } ?: run {
            return null
        }
    }

    @TypeConverter
    fun stringToArray(value: String?): ArrayList<ExpensesData>? {
        value?.let {
            val objects = GsonBuilder().create().fromJson(it, Array<ExpensesData>::class.java)
            return objects.toCollection(ArrayList())
        } ?: run {
            return null
        }
    }
}