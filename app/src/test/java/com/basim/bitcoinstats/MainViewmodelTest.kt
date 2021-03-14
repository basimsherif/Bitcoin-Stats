package com.basim.bitcoinstats

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.basim.bitcoinstats.data.repository.Repository
import com.basim.bitcoinstats.ui.main.MainViewModel
import com.basim.bitcoinstats.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
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

    /**
     * Unit test to verify API success scenario while getting chart details from API
     */
    @Test
    fun `given list of charts get charts should return success`() {
        testCoroutineRule.runBlockingTest {
            doReturn(getTestResponse())
                .`when`(repository)
                .getChartData("123")
            val viewModel = MainViewModel(repository)
            viewModel.chartDataLiveData.observeForever(chartDataObserver)
            viewModel.getChartData("123")
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
                .getChartData("123")
            val viewModel = MainViewModel(repository)
            viewModel.chartDataLiveData.observeForever(chartDataObserver)
            viewModel.getChartData("123")
            verify(chartDataObserver).onChanged(
                getTestResponse()
            )
            viewModel.chartDataLiveData.removeObserver(chartDataObserver)
        }
    }

}