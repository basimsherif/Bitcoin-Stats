package com.basim.bitcoinstats.data.repository

import androidx.lifecycle.LiveData
import com.basim.bitcoinstats.data.local.LocalDataSource
import com.basim.bitcoinstats.data.model.Chart
import com.basim.bitcoinstats.data.remote.RemoteDataSource
import com.basim.bitcoinstats.utils.Utils
import javax.inject.Inject

/**
 * Repository class used to hold all api/localdb operation
 * @param localDataSource local data source
 * @param firebaseSource Firebase data source
 */
class Repository @Inject constructor(
    private val remoteSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    /**
     * Get the charts from local DB as LiveData
     */
    fun getAllCharts(): LiveData<List<Chart>> {
        return localDataSource.getAllCharts()
    }

    /**
     * Insert the pre defined set of chart details to local DB
     */
    suspend fun insertCharts() {
        Utils.getCharts().let {
            localDataSource.insertAllCharts(it)
        }
    }

    /**
     * Get the selected chart data from API
     */
    suspend fun getChartData(chartId: String) =
        remoteSource.getChartDetails(chartId)

}