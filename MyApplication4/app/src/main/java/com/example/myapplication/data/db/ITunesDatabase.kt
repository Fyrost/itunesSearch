package com.example.myapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.db.entity.ITunesResult

@Database(
    entities = [ITunesResult::class],
    version = 1
)
abstract class ITunesDatabase: RoomDatabase() {
    abstract fun iTunesResultDao(): ITunesResultDao

    companion object {
        @Volatile private var instance: ITunesDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                ITunesDatabase::class.java, "itunes_result.db")
                .build()
    }
}