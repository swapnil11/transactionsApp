package com.example.finalplayground.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val bsb: String,
    val accountNumber: String,
    val balance: String,
    val available: String,
    val accountName: String,
    var pendingBalance: Float? = null
)
