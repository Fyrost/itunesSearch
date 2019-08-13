package com.example.myapplication.data.db


import androidx.room.*

import com.example.myapplication.data.db.entity.ITunesResult


@Dao
interface ITunesResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(resultList: ITunesResult)

    @Delete
    fun deleteResult(resultList: ITunesResult)

    @Query("SELECT * FROM itunes_result WHERE trackName LIKE :term AND kind = :media")
    fun getMediaTermResult(term: String, media: String): List<ITunesResult>

    @Query("SELECT * FROM itunes_result WHERE kind = :media")
    fun getMediaResult(media: String): List<ITunesResult>

    @Query("SELECT * FROM itunes_result")
    fun getAll(): List<ITunesResult>

    @Query("SELECT COUNT(*) FROM itunes_result WHERE trackId = :id")
    fun getSingleResult(id: Int): Int
}
