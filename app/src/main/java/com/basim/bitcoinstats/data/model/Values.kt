package com.basim.bitcoinstats.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Values(
    val x: Float,
    val y: Float
)