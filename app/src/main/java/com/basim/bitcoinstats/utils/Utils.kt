package com.basim.bitcoinstats.utils

import com.basim.bitcoinstats.R
import com.basim.bitcoinstats.data.model.Chart

/**
 * Utility class which contains all utility methods
 */
object Utils {
    fun getCharts(): List<Chart> {
        val chart1 = Chart().apply {
            id = "market-price"
            name = "Market Price"
            description = "The average USD market price across major bitcoin exchanges."
            iconId = R.drawable.dollar
        }
        val chart2 = Chart().apply {
            id = "total-bitcoins"
            name = "Total Circulating Bitcoin"
            description =
                "The total number of mined bitcoin that are currently circulating on the network."
            iconId = R.drawable.bitcoin
        }
        val chart3 = Chart().apply {
            id = "market-cap"
            name = "Market Capitalization"
            description = "The total USD value of bitcoin in circulation."
            iconId = R.drawable.dollar
        }
        return listOf(chart1, chart2, chart3)
    }
}