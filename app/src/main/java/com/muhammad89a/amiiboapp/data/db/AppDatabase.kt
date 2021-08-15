package com.muhammad89a.amiiboapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.muhammad89a.amiiboapp.data.model.AmiiboDTO

@Database(entities = [AmiiboDTO::class], version = 13, exportSchema = false)
@TypeConverters(value = [Converters::class])
abstract class AppDatabase : RoomDatabase(){
    abstract fun amiiboDao(): AmiiboDao
}