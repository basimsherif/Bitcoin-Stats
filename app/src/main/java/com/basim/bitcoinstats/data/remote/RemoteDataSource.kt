package com.basim.bitcoinstats.data.remote

import com.basim.bitcoinstats.utils.CountingIdlingResource
import com.basim.bitcoinstats.utils.Resource
import retrofit2.Response
import javax.inject.Inject

/**
 * Data source class used for remote operations
 * @param service service object
 */
class RemoteDataSource @Inject constructor(
    private val service: Service
) {

    suspend fun getChartDetails(chartId: String): Resource<Any> =
        getResult { service.getChartDetails(chartId) }

    /**
     * A method to parse API response
     */
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            //Increment the CountingIdlingResource to notify the espresso to start waiting for API call
            CountingIdlingResource.increment()
            val response = call()
            if (response.isSuccessful) {
                //Decrement the CountingIdlingResource to notify the espresso that API is success
                CountingIdlingResource.decrement()
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            //Decrement the CountingIdlingResource to notify the espresso that API is thrown exception
            CountingIdlingResource.decrement()
            return error(e.message ?: e.toString())
        }
    }

    /**
     * A method to handle API error
     */
    private fun <T> error(message: String): Resource<T> {
        //Decrement the CountingIdlingResource to notify the espresso that API is thrown an error
        CountingIdlingResource.decrement()
        //Print the specific error here so that we can diagnose. But a generic error message will be shown to user
        return Resource.error("Network call has failed for a following reason: $message")
    }


}
