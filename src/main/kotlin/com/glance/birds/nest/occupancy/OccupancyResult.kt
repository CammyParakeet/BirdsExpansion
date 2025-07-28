package com.glance.birds.nest.occupancy

sealed class OccupancyResult {
    data object Assigned : OccupancyResult()
    data object Full : OccupancyResult()
    data object AlreadyAssigned : OccupancyResult()
    data object NotInitialized : OccupancyResult()
}