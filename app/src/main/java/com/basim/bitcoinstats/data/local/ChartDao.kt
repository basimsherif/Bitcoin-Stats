package com.basim.bitcoinstats.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.basim.bitcoinstats.data.model.Chart

/**
 * DAO class for database operations for charts
 */
@Dao
interface ChartDao {

    /**
     * Get all charts from local DB
     */
    @Query("SELECT * FROM chart_table")
    fun getAllChartsLiveData(): LiveData<List<Chart>>

    /**
     * Insert list of charts to local DB
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(imageList: List<Chart>)
}