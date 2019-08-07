package com.example.myapplication.data.db.network.response


import com.example.myapplication.data.db.entity.ITunesResult
import com.google.gson.annotations.SerializedName

data class ITunesResponse(
    val resultCount: Int,
    @SerializedName("results")
    val iTunesResult: List<ITunesResult>
)