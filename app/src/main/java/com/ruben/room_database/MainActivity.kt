package com.ruben.room_database

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.ruben.room_database.databinding.ActivityMainBinding
import com.ruben.room_database.network.QuoteService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val api = QuoteService()
        binding.loading.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val response: List<QuoteModel> = api.getQuotes()
            QuoteProvider.quotes = response
            runOnUiThread {
                initUI()
                binding.loading.isVisible = false
            }
        }
        binding.viewContainer.setOnClickListener { initUI() }
    }

    private fun initUI() {
        val quoteModel = QuoteProvider.random()
        binding.tvQuote.text = quoteModel.quote
        binding.tvAuthor.text = quoteModel.author
    }
}