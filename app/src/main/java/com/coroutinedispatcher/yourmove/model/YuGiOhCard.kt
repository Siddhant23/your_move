package com.coroutinedispatcher.yourmove.model

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
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
) : Parcelable