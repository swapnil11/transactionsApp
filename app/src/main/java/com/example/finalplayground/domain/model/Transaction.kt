package com.example.finalplayground.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val amount: String,
    val id: String,
    val isPending: Boolean,
    val description: String,
    val category: Category,
    val effectiveDate: LocalDate,
    val atmId: String? = null
) : java.io.Serializable
