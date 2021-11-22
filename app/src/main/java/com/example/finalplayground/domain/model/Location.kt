package com.example.finalplayground.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val lat: Float,
    val lon: Float
) : java.io.Serializable
