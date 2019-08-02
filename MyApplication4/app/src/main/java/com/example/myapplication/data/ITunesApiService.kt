package com.example.myapplication.data

import com.example.myapplication.data.db.network.ConnectivityInterceptor
import com.example.myapplication.data.db.network.response.ITunesResponse
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://itunes.apple.com/search?term=toy+story
interface ITunesApiService {

    @GET("search")
    fun getResults(
        @Query("term") term: String,
        @Query("media") media: String
    ): Deferred<ITunesResponse>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): ITunesApiService {
            var okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://itunes.apple.com/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
                .build()
                .create(ITunesApiService::class.java)
        }
    }
}