package com.basim.bitcoinstats.utils

import androidx.test.espresso.idling.CountingIdlingResource

/**
 * Static implementation of Espresso counting resource
 */
object CountingIdlingResource {

    private const val RESOURCE = "GLOBAL"

    @JvmField val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}