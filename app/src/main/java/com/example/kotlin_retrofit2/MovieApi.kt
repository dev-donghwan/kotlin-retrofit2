package com.example.kotlin_retrofit2

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("webservice/rest/boxoffice/searchDailyBoxOfficeList.json")
    fun getBoxOffice(
        @Query("key") key: String,
        @Query("targetDt") targetDt: String
    ): Single<Data>
}