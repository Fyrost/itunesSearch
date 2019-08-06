package com.example.myapplication.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "itunes_result")
data class ITunesResult(
    @PrimaryKey(autoGenerate = false)
    val trackId: Int,
    val artistName: String?,
    val artworkUrl100: String?,
    val kind: String?,
    val longDescription: String?,
    val releaseDate: String?,
    val trackName: String?,
    val trackPrice: Double?,
    val primaryGenreName: String?
): Parcelable