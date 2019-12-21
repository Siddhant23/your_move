package com.coroutinedispatcher.yourmove.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CardImage(
    @Json(name = "id")
    val id: String,
    @Json(name = "image_url")
    val imageUrl: String,
    @Json(name = "image_url_small")
    val smallImageUrl: String
)