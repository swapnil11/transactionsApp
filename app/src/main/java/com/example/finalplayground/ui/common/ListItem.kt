package com.example.finalplayground.ui.common

import com.example.finalplayground.domain.model.Atm
import com.example.finalplayground.domain.model.Transaction
import kotlinx.datetime.LocalDate

sealed class ListItem {
    abstract val viewType: Int

    companion object {
        const val TYPE_DATE_ITEM = 0
        const val TYPE_TRANSACTION_ITEM = 1
    }
}

data class DateItem(
    val localDate: LocalDate,
    override val viewType: Int = TYPE_DATE_ITEM
) : ListItem()

data class TransactionItem(
    val transaction: Transaction,
    val atm: Atm?,
    override val viewType: Int = TYPE_TRANSACTION_ITEM
) : ListItem()
