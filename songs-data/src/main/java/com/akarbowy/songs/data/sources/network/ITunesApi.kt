package com.akarbowy.songs.data.sources.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

internal interface ITunesApi {

    @GET("search")
    fun searchSongs(
        @Query("term") artist: String,
        @QueryMap entity: Map<String, String>
    ): Single<SearchResponse>

    companion object {
        const val BASE_URL = "https://itunes.apple.com/"
    }

}