package com.basim.bitcoinstats

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.basim.bitcoinstats.data.local.AppDatabase
import com.basim.bitcoinstats.data.local.ChartDao
import com.basim.bitcoinstats.utils.Utils
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class InstrumentationTests {
    private lateinit var chartDao: ChartDao
    private lateinit var db: AppDatabase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        chartDao = db.chartDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    /**
     * Instrumented test to verify ROOM for adding charts to local DB and retrieving it
     */
    @Test
    @Throws(Exception::class)
    fun verifyWriteChartAndReadList() = runBlocking {
        //Get the chart list and insert to DB
        val chartListFromUtil = Utils.getCharts()
        chartDao.insertAll(chartListFromUtil)
        //Wait for LiveData to get notify
        val chartList = chartDao.getAllChartsLiveData().blockingObserve()
        //Assert the result
        assert(chartList?.size == 3)
        assert(chartList?.get(0) == chartListFromUtil[0])
        assert(chartList?.get(1) == chartListFromUtil[1])
        assert(chartList?.get(2) == chartListFromUtil[2])
    }
}