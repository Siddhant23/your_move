package com.coroutinedispatcher.yourmove.model

import com.google.firebase.database.IgnoreExtraProperties
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Card(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "desc")
    val description: String,
    @Json(name = "atk")
    val attackPoints: String?,
    @Json(name = "def")
    val deffencePoints: String?,
    @Json(name = "level")
    val level: String?,
    @Json(name = "race")
    val race: String?,
    @Json(name = "attribute")
    val attribute: String?,
    @Json(name = "card_images")
    val cardImages: List<CardImage>
)

@IgnoreExtraProperties
data class YuGiOhCard(
    val id: String? = "",
    val name: String? = "",
    val type: String? = "",
    val description: String? = "",
    val attackPoints: String? = "",
    val deffencePoints: String? = "",
    val level: String? = "",
    val race: String? = "",
    val attribute: String? = "",
    val cardImages: List<YuGiOhCardImage>? = listOf(),
    val imageUrl: String? = "",
    val imageUrlSmall: String? = ""
)