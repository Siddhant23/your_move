package com.coroutinedispatcher.yourmove.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.coroutinedispatcher.yourmove.model.YuGiOhCard

@Dao
interface YuGiOhDao {
    @Query("SELECT * FROM yugioh_cards ORDER BY name")
    fun selectAll(): DataSource.Factory<Int, YuGiOhCard>
}