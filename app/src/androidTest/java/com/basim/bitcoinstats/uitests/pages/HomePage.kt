package com.basim.bitcoinstats.uitests.pages

import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.basim.bitcoinstats.R
import com.basim.bitcoinstats.atPosition
import com.basim.bitcoinstats.clickItemWithId
import com.basim.bitcoinstats.data.model.Chart
import com.basim.bitcoinstats.ui.main.ChartAdapter
import com.basim.bitcoinstats.withDrawable


/**
 * This class contains all the functions related to Home page and maintains a Page Object Model
 */
class HomePage {
    var TAG = "Home page"

    /**
     * This function is used to verify home page items
     */
    fun verifyHomePageItems() {
        Log.d(TAG, "Verify if title is shown")
        onView(withId(R.id.titleTextView)).check(matches(withText(R.string.app_name)))
        Log.d(TAG, "Verify if chart list is shown")
        onView(withId(R.id.chartsRecyclerView)).check(matches(isDisplayed()))
        Log.d(TAG, "Verify if span text is shown")
        onView(withId(R.id.spanText)).check(matches(withText(R.string.span)))
    }

    /**
     * This function is used to verify chart recyler view
     */
    fun verifyChartRecyclerView(chartList: List<Chart>) {
        for (i in chartList.indices) {
            Log.d(TAG, "Verify if recycler view title shown properly in position $i")
            onView(withId(R.id.chartsRecyclerView))
                .check(matches(atPosition(i, hasDescendant(withText(chartList[i].name)))))
            Log.d(TAG, "Verify if recycler view image shown properly in position $i")
            onView(withId(R.id.chartsRecyclerView))
                .check(matches(atPosition(i, hasDescendant(withDrawable(chartList[i].iconId)))))
        }
    }

    fun tapRecyclerViewItem(position:Int){
        onView(withId(R.id.chartsRecyclerView))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<ChartAdapter.ChartViewHolder>(
                        position,
                        clickItemWithId(R.id.cardView)
                    )
            )
    }

    /**
     * This function is used to verify chart recyler view click
     */
    fun verifyChartRecyclerViewClick(chartList: List<Chart>) {
        Log.d(TAG, "Tap Chart recyclerview item")
        for (i in chartList.indices) {
            tapRecyclerViewItem(i)
            Log.d(TAG, "Verify if title text is shown")
            onView(withId(R.id.chartTitleText)).check(matches(withText(chartList[i].name)))
            Log.d(TAG, "Verify if description text is shown")
            onView(withId(R.id.chartDescText)).check(matches(withText(chartList[i].description)))
            Log.d(TAG, "Verify if chart is shown")
            onView(withId(R.id.chart)).check(matches(isDisplayed()))

        }
    }

    fun verifyErrorSnackBar(){
        onView(withText(R.string.network_error)).check(matches(isDisplayed()))
    }
}