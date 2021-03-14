package com.basim.bitcoinstats.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.basim.bitcoinstats.R
import com.basim.bitcoinstats.data.model.Chart
import com.basim.bitcoinstats.databinding.ItemChartBinding
import com.basim.bitcoinstats.utils.OnListItemClickListener

/**
 * Adapter class for Chart recyclerview
 */
class ChartAdapter : RecyclerView.Adapter<ChartAdapter.ChartViewHolder>() {
    var data: List<Chart> = ArrayList(1)
    lateinit var onClick: OnListItemClickListener
    var selectedItem = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            R.layout.item_chart,
            parent,
            false
        ) as ItemChartBinding
        return ChartViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setSelected(position: Int) {
        selectedItem = position
    }

    class ChartViewHolder(itemBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        var rowBinding = itemBinding as ItemChartBinding
        fun bind(
            chartDetails: Chart,
            onClick: OnListItemClickListener?,
            position: Int,
            selectedItem: Int
        ) {
            rowBinding.data = chartDetails
            rowBinding.callBack = onClick
            rowBinding.index = position
            if (selectedItem == position) {
                rowBinding.cardView.cardElevation = 20f
                rowBinding.root.setBackgroundResource(R.drawable.card_selected_bg)
            } else {
                rowBinding.cardView.cardElevation = 0f
                rowBinding.root.setBackgroundResource(R.drawable.card_bg)
            }
        }

    }

    fun setAdapterData(items: List<Chart>) {
        data = items
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ChartViewHolder, position: Int) {
        if (data.isNotEmpty()) {
            holder.bind(data[position], onClick, position, selectedItem)
        }
    }

}