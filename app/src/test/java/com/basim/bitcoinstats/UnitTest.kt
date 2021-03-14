package com.basim.bitcoinstats

import com.basim.bitcoinstats.utils.Utils
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTest {

    @Test
    fun `should return proper chart list`() {
        val chartList = Utils.getCharts()
        assertTrue(chartList.size == 3)
        assertTrue(
            chartList[0].id == "market-price"
                    && chartList[0].name == "Market Price"
                    && chartList[0].iconId == R.drawable.dollar
        )
        assertTrue(
            chartList[1].id == "total-bitcoins"
                    && chartList[1].name == "Total Circulating Bitcoin"
                    && chartList[1].iconId == R.drawable.bitcoin
        )
        assertTrue(
            chartList[2].id == "market-cap"
                    && chartList[2].name == "Market Capitalization"
                    && chartList[2].iconId == R.drawable.dollar
        )

        assertTrue(false)
    }
}