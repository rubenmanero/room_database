package com.ruben.room_database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ruben.room_database.database.dao.QuoteDao
import com.ruben.room_database.database.entities.QuoteEntity

@Database(entities = [QuoteEntity::class], version = 1)
abstract class QuoteDatabase: RoomDatabase() {
    abstract fun getQuoteDao(): QuoteDao
}