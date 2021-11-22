package com.example.finalplayground.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Atm(
    val id: String,
    val name: String,
    val location: Location? = null,
    val address: String
) : java.io.Serializable
