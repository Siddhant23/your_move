package com.coroutinedispatcher.yourmove.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.coroutinedispatcher.yourmove.model.YuGiOhCard

@Database(entities = [YuGiOhCard::class], version = 1)
abstract class YuGiOhDatabase : RoomDatabase() {
    abstract fun yuGiOhDao(): YuGiOhDao
}