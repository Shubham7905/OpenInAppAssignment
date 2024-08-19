package com.openin.openinapp.repository

import com.github.mikephil.charting.data.Entry
import com.openin.openinapp.R
import com.openin.openinapp.model.ApiResponse
import com.openin.openinapp.model.CardModel
import com.openin.openinapp.retrofit.RetrofitClient
import java.util.Calendar

class LinksRepository {
    suspend fun getLinks(token: String): ApiResponse {
        return RetrofitClient.api.getLinks("Bearer $token")
    }

    fun getUsers(): List<CardModel> {
        return listOf(
            CardModel("123", "Today's clicks", R.drawable.image1),
            CardModel("Ahamedab..", "Top Location", R.drawable.image2),
            CardModel("Instagram", "Top source", R.drawable.image3),
            CardModel("Best Time", "11:00 - 12:00", R.drawable.image4)
        )
    }

    fun getGreetingMessage(): String {
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        return when {
            currentHour < 12 -> "Good Morning"
            currentHour < 16 -> "Good Afternoon"
            else -> "Good Evening"
        }
    }

    fun getLineChartData(): List<Entry> {
        return listOf(
            Entry(0f, 4f),
            Entry(1f, 8f),
            Entry(2f, 6f),
            Entry(3f, 12f),
            Entry(4f, 18f),
            Entry(5f, 9f)
        )
    }
}