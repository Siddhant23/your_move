package com.coroutinedispatcher.yourmove.db

import androidx.room.Dao
import androidx.room.Query
import com.coroutinedispatcher.yourmove.model.YuGiOhCard

@Dao
interface YuGiOhDao {
    @Query("SELECT * FROM yugioh_cards")
    suspend fun selectAll(): List<YuGiOhCard>
}