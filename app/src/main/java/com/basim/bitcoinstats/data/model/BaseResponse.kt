package com.basim.bitcoinstats.data.model

import com.squareup.moshi.JsonClass

/**
 * Data class for Base response
 */
@JsonClass(generateAdapter = true)
data class BaseResponse(
    val status: String,
    val name: String,
    val unit: String,
    val period: String,
    val description: String,
    val values: List<Values>
)