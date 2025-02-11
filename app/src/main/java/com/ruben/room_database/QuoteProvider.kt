package com.ruben.room_database

class QuoteProvider {
    companion object {
        var quotes: List<QuoteModel> = emptyList()
        fun random(): QuoteModel {
            val position: Int = quotes.indices.random()
            return quotes[position]
        }
    }
}
