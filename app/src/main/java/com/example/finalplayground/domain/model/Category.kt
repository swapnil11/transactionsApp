package com.example.finalplayground.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Category {

    @SerialName("business")
    BUSINESS,
    @SerialName("cards")
    CARDS,
    @SerialName("cash")
    CASH,
    @SerialName("categories")
    CATEGORIES,
    @SerialName("eatingOut")
    EATING_OUT,
    @SerialName("education")
    EDUCATION,
    @SerialName("entertainment")
    ENTERTAINMENT,
    @SerialName("groceries")
    GROCERIES,
    @SerialName("shopping")
    SHOPPING,
    @SerialName("transport")
    TRANSPORTATION,
    @SerialName("travel")
    TRAVEL,
    @SerialName("uncategorised")
    UNCATEGORISED;
}
