package com.coroutinedispatcher.yourmove.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.coroutinedispatcher.yourmove.model.YuGiOhCard

@Dao
interface YuGiOhDao {
    @Query("SELECT * FROM yugioh_cards ORDER BY name")
    fun selectAll(): DataSource.Factory<Int, YuGiOhCard>

    @Query("SELECT DISTINCT type FROM yugioh_cards")
    fun selectAllTypes(): List<String>

    @Query("SELECT DISTINCT race FROM yugioh_cards")
    fun selectAllRaces(): List<String>

    @RawQuery(observedEntities = [YuGiOhCard::class])
    fun selectAllMeetingCondition(query: SupportSQLiteQuery): DataSource.Factory<Int, YuGiOhCard>
}