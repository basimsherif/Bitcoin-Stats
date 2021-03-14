package com.basim.bitcoinstats.data.local

import androidx.lifecycle.LiveData
import com.basim.bitcoinstats.data.model.Chart
import javax.inject.Inject

/**
 * Local source class used to communicate with local DB
 */
class LocalDataSource @Inject constructor(
    private val chartDao: ChartDao
) {
    /**
     * Insert a list of Charts to local DB
     */
    suspend fun insertAllCharts(chartList: List<Chart>) {
        chartDao.insertAll(chartList)
    }

    /**
     * Get the charts from local DB as LiveData
     */
    fun getAllCharts(): LiveData<List<Chart>> {
        return chartDao.getAllChartsLiveData()
    }
}