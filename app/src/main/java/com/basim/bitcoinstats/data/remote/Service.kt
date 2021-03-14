package com.basim.bitcoinstats.data.remote

import com.basim.bitcoinstats.data.model.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Service class used for Retrofit
 */
interface Service {
    /***
     * GET method for getting chart detail from API
     */
    @GET("{id}?timespan=180days&rollingAverage=8hours&format=json")
    suspend fun getChartDetails(@Path("id") id: String): Response<BaseResponse>
}