package com.coroutinedispatcher.yourmove.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Card(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name="type")
    val type: String,
    @Json(name="desc")
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