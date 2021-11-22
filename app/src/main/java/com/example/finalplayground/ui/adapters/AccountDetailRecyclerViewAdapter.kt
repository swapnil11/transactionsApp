package com.example.finalplayground.ui.adapters

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.bold
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalplayground.BR
import com.example.finalplayground.R
import com.example.finalplayground.databinding.LayoutDateItemBinding
import com.example.finalplayground.databinding.LayoutTransactionItemBinding
import com.example.finalplayground.domain.model.AccountDetail
import com.example.finalplayground.domain.model.Transaction
import com.example.finalplayground.ui.common.DateItem
import com.example.finalplayground.ui.common.ListItem
import com.example.finalplayground.ui.common.TransactionItem
import com.example.finalplayground.ui.common.toDateContentHolder
import kotlinx.datetime.LocalDate

class AccountDetailRecyclerViewAdapter(
    private val data: AccountDetail,
    private val mapOfTransactions: Map<LocalDate, List<Transaction>>,
    private val clickListener: ItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = mutableListOf<ListItem>()

    init {
        // using the map of transaction with date as key and a list of transaction for the same to be transformated
        // into list of [ListItem]'s which is to be used by the recyclviewAdapter to render the UI.
        mapOfTransactions.forEach { it ->
            items.add(DateItem(it.key))
            it.value.forEach { transaction ->
                items.add(
                    TransactionItem(
                        transaction,
                        data.atms.firstOrNull { it.id == transaction.atmId }
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            ListItem.TYPE_TRANSACTION_ITEM -> {
                val binding = DataBindingUtil.inflate<LayoutTransactionItemBinding>(
                    inflater,
                    R.layout.layout_transaction_item,
                    parent,
                    false
                )
                return TransactionItemHolder(binding, clickListener)
            }
            else -> {
                val binding = DataBindingUtil.inflate<LayoutDateItemBinding>(
                    inflater,
                    R.layout.layout_date_item,
                    parent,
                    false
                )
                return DateItemHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ListItem.TYPE_TRANSACTION_ITEM -> {
                val transactionItem = items[position] as TransactionItem
                (holder as TransactionItemHolder).bind(transactionItem)
            }
            else -> {
                val dateItem = items[position] as DateItem
                (holder as DateItemHolder).bind(dateItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    fun setItems(items: List<ListItem>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class DateItemHolder(
        private val binding: LayoutDateItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DateItem) {
            binding.setVariable(BR.item, item.localDate.toDateContentHolder(itemView.context))
        }
    }

    class TransactionItemHolder(
        private val binding: LayoutTransactionItemBinding,
        private val clickListener: ItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TransactionItem) {
            binding.setVariable(BR.item, item.transaction)

            if (item.transaction.isPending) {
                val spannedString = SpannableStringBuilder()
                    .bold { append(itemView.context.getString(R.string.transaction_pending_label)) }
                    .append(HtmlCompat.fromHtml(item.transaction.description, HtmlCompat.FROM_HTML_MODE_LEGACY))
                binding.transactionDescription.text = spannedString
            } else {
                binding.transactionDescription.text = HtmlCompat.fromHtml(item.transaction.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
            }

            itemView.setOnClickListener {
                clickListener.onItemClick(item)
            }
        }
    }
}

interface ItemClickListener {
    fun onItemClick(item: Any?)
}
