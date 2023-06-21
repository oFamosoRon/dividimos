package com.ofamosoron.dividimos.data.database

import androidx.room.*
import com.ofamosoron.dividimos.data.entities.CheckEntity
import com.ofamosoron.dividimos.data.entities.DishesEntity
import com.ofamosoron.dividimos.data.entities.GuestsEntity

@Database(entities = [DishesEntity::class, GuestsEntity::class, CheckEntity::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class SplitzDatabase : RoomDatabase() {
    abstract val databaseDao: DividimosDao
}

class StringListConverter {
    @TypeConverter
    fun fromString(value: String?): List<String> {
        return value?.split(",") ?: emptyList()
    }

    @TypeConverter
    fun toString(list: List<String>): String {
        return list.joinToString(",")
    }
}