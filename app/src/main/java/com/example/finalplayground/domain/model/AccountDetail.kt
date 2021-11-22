package com.example.finalplayground.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AccountDetail(
    val account: Account,
    val transactions: List<Transaction>,
    val atms: List<Atm>
) {
    init {
        account.pendingBalance = transactions.filter { it.isPending }.map { it.amount.toFloat() }.sum()
    }
}
