package com.basim.bitcoinstats.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.basim.bitcoinstats.R
import com.basim.bitcoinstats.data.model.BaseResponse
import com.basim.bitcoinstats.data.model.Chart
import com.basim.bitcoinstats.data.model.Values
import com.basim.bitcoinstats.databinding.MainFragmentBinding
import com.basim.bitcoinstats.utils.OnListItemClickListener
import com.basim.bitcoinstats.utils.Resource
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*


/**
 * Fragment class for home screen
 */
@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment), OnListItemClickListener {
    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = MainFragmentBinding.bind(view)
        binding.homeViewModel = viewModel
        binding.homeFragment = this
        viewModel.chartAdapter.onClick = this
        val mLayoutManager = LinearLayoutManager(requireContext())
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        chartsRecyclerView.layoutManager = mLayoutManager
        observeData()
    }

    override fun onListItemClick(view: View, obj: Any, index: Int) {
        viewModel.setSelectedChart(obj as Chart, index)
    }

    /**
     * Observe all livedata to get notified and update the UI
     */
    private fun observeData() {
        //Observing livedata for listing the charts
        viewModel.chartsLiveData.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                viewModel.chartAdapter.setAdapterData(it)
                //Set first chart as selected
                viewModel.setSelectedChart(it[0], 0)
            }
        })

        //Observing livedata selecting a chart
        viewModel.selectedChartLiveData.observe(viewLifecycleOwner, Observer {
            chartTitleText.text = it.name
            chartDescText.text = it.description
            viewModel.getChartData(it.id)
        })

        //Observe the livedata for listening to API call backs and updating the UI
        viewModel.chartDataLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    //Hide progress bar after loading
                    loadingView.visibility = View.GONE
                    val data = it.data as BaseResponse
                    setupChart(data.values)
                    chart.visibility = View.VISIBLE
                }
                Resource.Status.ERROR -> {
                    //Hide progress bar after loading
                    loadingView.visibility = View.GONE
                    Snackbar.make(requireView(), R.string.network_error, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.retry) {
                            //Retry loading data from API if failed
                            viewModel.getChartData(viewModel.selectedChartLiveData.value?.id!!)
                        }
                        .show()
                }
                Resource.Status.LOADING -> {
                    //Show progress bar for loading
                    loadingView.visibility = View.VISIBLE
                    chart.visibility = View.GONE
                }
            }
        })
    }

    /**
     * Setup the chart once API loaded successfully
     */
    private fun setupChart(values: List<Values>) {
        chart.xAxis.isEnabled = false
        chart.axisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        chart.axisLeft.textColor =
            ContextCompat.getColor(requireContext(), R.color.card_selected_bg_color2)
        chart.axisRight.isEnabled = false
        chart.legend.isEnabled = false
        chart.description.isEnabled = false
        chart.setViewPortOffsets(0f, 0f, 0f, 0f)
        var entryList = mutableListOf<Entry>()
        values.forEach {
            entryList.add(Entry(it.x, it.y))
        }
        chart.animateX(1000)
        val dataSet = LineDataSet(entryList as List<Entry>, "DataSet 1")
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSet.lineWidth = 2f
        dataSet.circleRadius = 3f
        dataSet.setDrawCircles(false)
        dataSet.formLineWidth = 1f
        dataSet.formSize = 15f
        dataSet.isHighlightEnabled = false
        dataSet.color = ContextCompat.getColor(requireContext(), R.color.card_selected_bg_color2)
        dataSet.setDrawFilled(true)
        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.chart_fill)
        dataSet.fillDrawable = drawable
        val data = LineData(dataSet)
        chart.data = data
    }
}