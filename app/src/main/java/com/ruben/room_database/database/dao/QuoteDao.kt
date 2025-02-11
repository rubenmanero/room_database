package com.ruben.room_database.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruben.room_database.QuoteModel
import com.ruben.room_database.database.entities.QuoteEntity

@Dao
interface QuoteDao {
    @Query("SELECT * FROM quote_table ORDER BY author DESC")
    suspend fun getAllQuotes(): List<QuoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quotes: List<QuoteEntity>)

    @Query("DELETE FROM sqlite_sequence WHERE name = 'quote_table'")
    suspend fun deletePrimaryKeyIndex()

    @Query("DELETE FROM quote_table")
    suspend fun deleteAllQuotes()
}