package com.openin.openinapp.model

import java.text.SimpleDateFormat
import java.util.Locale

data class Link(
    val url_id: Int,
    val web_link: String,
    val smart_link: String,
    val title: String,
    val total_clicks: Int,
    val original_image: String?,
    val thumbnail: String?,
    val times_ago: String,
    val created_at: String,
    val domain_id: String,
    val url_prefix: String?,
    val url_suffix: String,
    val app: String,
    val is_favourite: Boolean
){
    val formattedDate: String
        get() {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            val date = inputFormat.parse(created_at)
            return date?.let { outputFormat.format(it) } ?: "N/A"
        }
}