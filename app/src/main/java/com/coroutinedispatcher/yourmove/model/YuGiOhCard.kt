package com.coroutinedispatcher.yourmove.model

import com.google.firebase.database.IgnoreExtraProperties

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
    val imageUrl: String? = "",
    val imageUrlSmall: String? = ""
)