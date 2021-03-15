package com.basim.bitcoinstats

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.basim.bitcoinstats.data.model.Chart
import com.basim.bitcoinstats.data.repository.Repository
import com.basim.bitcoinstats.ui.main.ChartAdapter
import com.basim.bitcoinstats.ui.main.MainViewModel
import com.basim.bitcoinstats.utils.Resource
import com.basim.bitcoinstats.utils.Utils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewmodelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var chartDataObserver: Observer<Resource<Any>>

    @Mock
    private lateinit var chartAdapter: ChartAdapter

    @Mock
    private lateinit var selectedChartObserver: Observer<Chart>

    /**
     * Unit test to verify API success scenario while getting chart details from API
     */
    @Test
    fun `given list of charts get charts should return success`() {
        testCoroutineRule.runBlockingTest {
            doReturn(getTestResponse())
                .`when`(repository)
                .getChartData("market-price")
            val viewModel = MainViewModel(repository)
            viewModel.chartDataLiveData.observeForever(chartDataObserver)
            viewModel.getChartData("market-price")
            verify(chartDataObserver).onChanged(getTestResponse())
            viewModel.chartDataLiveData.removeObserver(chartDataObserver)
        }
    }

    /**
     * Unit test to verify API error scenario while getting chart details from API
     */
    @Test
    fun `given server response should return error`() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message"
            doReturn(Resource.error(errorMessage, null))
                .`when`(repository)
                .getChartData("market-price")
            val viewModel = MainViewModel(repository)
            viewModel.chartDataLiveData.observeForever(chartDataObserver)
            viewModel.getChartData("market-price")
            verify(chartDataObserver).onChanged(
                Resource.error(errorMessage, null)
            )
            viewModel.chartDataLiveData.removeObserver(chartDataObserver)
        }
    }

    /**
     * Unit test to verify selected chart live data and chart adapter notify working properly
     */
    @Test
    fun `chart adapter notified when item selected`() {
        testCoroutineRule.runBlockingTest {
            val viewModel = MainViewModel(repository)
            val items = Utils.getCharts()
            viewModel.chartAdapter = chartAdapter
            viewModel.chartAdapter.setAdapterData(items)
            viewModel.selectedChartLiveData.observeForever(selectedChartObserver)
            viewModel.setSelectedChart(items[1], 1)
            verify(viewModel.chartAdapter).notifyDataSetChanged()
            verify(selectedChartObserver).onChanged(
                items[1]
            )
            viewModel.selectedChartLiveData.removeObserver(selectedChartObserver)

        }
    }

}