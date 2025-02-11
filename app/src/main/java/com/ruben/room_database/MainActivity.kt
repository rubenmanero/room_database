package com.ruben.room_database

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.room.Room
import com.ruben.room_database.database.QuoteDatabase
import com.ruben.room_database.database.dao.QuoteDao
import com.ruben.room_database.database.entities.QuoteEntity
import com.ruben.room_database.database.entities.toDatabase
import com.ruben.room_database.databinding.ActivityMainBinding
import com.ruben.room_database.network.QuoteService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var quoteDB: QuoteDatabase
    private lateinit var quoteDao: QuoteDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quoteDB = Room.databaseBuilder(this, QuoteDatabase::class.java, "quote_database").build()
        quoteDao = quoteDB.getQuoteDao()


        val api = QuoteService()
        binding.loading.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val response: List<QuoteModel> = api.getQuotes()
            if(response.isNotEmpty()) {
                val entities: List<QuoteEntity> = response.map{ it.toDatabase()}
                quoteDao.deletePrimaryKeyIndex()
                quoteDao.deleteAllQuotes()
                quoteDao.insertAll(entities)
            }
            val quotesDB: List<QuoteEntity> = quoteDao.getAllQuotes()
            runOnUiThread {
                initUI(quotesDB)
                binding.loading.isVisible = false
                binding.viewContainer.setOnClickListener { initUI(quotesDB) }
            }
        }
    }

    private fun initUI(quotes: List<QuoteEntity>) {
        val position: Int = quotes.indices.random()
        val quoteModel = quotes[position]
        binding.tvQuote.text = quoteModel.quote
        binding.tvAuthor.text = quoteModel.author
    }
}