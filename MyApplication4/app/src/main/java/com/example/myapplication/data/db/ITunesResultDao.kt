package com.example.myapplication.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.data.db.entity.ITunesResult

@Dao
interface ITunesResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(resultList: ITunesResult)

    @Query("SELECT * FROM itunes_result WHERE trackName LIKE :term AND kind = :media")
    fun getResults(term: String, media: String): LiveData<ITunesResult>
}