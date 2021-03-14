package com.basim.bitcoinstats.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class for Chart
 */
@Entity(tableName = "chart_table")
data class Chart(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var iconId: Int = 0
)