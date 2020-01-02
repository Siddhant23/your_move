package com.coroutinedispatcher.yourmove.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "yugioh_cards")
data class YuGiOhCard(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int? = 0,
    @ColumnInfo(name = "name")
    val name: String? = "",
    @ColumnInfo(name = "type")
    val type: String? = "",
    @ColumnInfo(name = "desc")
    val description: String? = "",
    @ColumnInfo(name = "race")
    val race: String? = "",
    @ColumnInfo(name = "atk")
    val attackPoints: Int? = -1,
    @ColumnInfo(name = "def")
    val deffencePoints: Int? = -1,
    @ColumnInfo(name = "level")
    val level: Int? = -1,
    @ColumnInfo(name = "attribute")
    val attribute: String? = ""
)