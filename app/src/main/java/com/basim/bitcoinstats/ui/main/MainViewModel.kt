package com.basim.bitcoinstats.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.basim.bitcoinstats.data.model.BaseResponse
import com.basim.bitcoinstats.data.model.Chart
import com.basim.bitcoinstats.data.repository.Repository
import com.basim.bitcoinstats.utils.CountingIdlingResource
import com.basim.bitcoinstats.utils.Resource
import kotlinx.coroutines.launch

/**
 * View model class for Home Fragment
 */
class MainViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {
    val chartAdapter: ChartAdapter = ChartAdapter()
    private val _chartsLiveData = repository.getAllCharts()
    val chartsLiveData: LiveData<List<Chart>> = _chartsLiveData
    private val _selectedChartLiveData = MutableLiveData<Chart>()
    val selectedChartLiveData: LiveData<Chart> = _selectedChartLiveData
    private val _chartDataLiveData = MutableLiveData<Resource<Any>>()
    val chartDataLiveData: LiveData<Resource<Any>> = _chartDataLiveData

    init {
        viewModelScope.launch {
            //Insert the initial list of charts to local DB
            repository.insertCharts()
        }
    }

    /**
     * Set the selected when a chart is selected in Recyclerview
     */
    fun setSelectedChart(chart: Chart, index: Int) {
        _selectedChartLiveData.value = chart
        chartAdapter.setSelected(index)
        chartAdapter.notifyDataSetChanged()
    }

    /**
     * Get the chart details from API and notify the change to livedata to update UI
     */
    fun getChartData(chartId: String) {
        _chartDataLiveData.postValue(Resource.loading())
        viewModelScope.launch {
            val apiResponse = repository.getChartData(chartId)
            apiResponse.let {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        var data = it.data as BaseResponse
                        _chartDataLiveData.postValue(Resource.success(data))
                    }
                    Resource.Status.ERROR -> {
                        _chartDataLiveData.postValue(Resource.error(it.message!!))
                    }
                }
            }
        }
    }
}