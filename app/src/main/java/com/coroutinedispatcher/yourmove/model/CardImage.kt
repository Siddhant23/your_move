package com.coroutinedispatcher.yourmove.model

import com.google.firebase.database.IgnoreExtraProperties
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@IgnoreExtraProperties
data class CardImage(
    @Json(name = "id")
    val id: String = "",
    @Json(name = "image_url")
    val imageUrl: String = "",
    @Json(name = "image_url_small")
    val imageUrlSmall: String = ""
)

@IgnoreExtraProperties
data class CardImageList(
    val list: List<YuGiOhCardImage> = listOf()
)

@IgnoreExtraProperties
data class YuGiOhCardImage(
    val id: String = "",
    val imageUrl: String = "",
    val imageUrlSmall: String = ""
)