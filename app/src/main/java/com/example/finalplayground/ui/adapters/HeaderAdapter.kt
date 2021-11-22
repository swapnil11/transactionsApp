package com.example.finalplayground.ui.adapters

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.color
import androidx.recyclerview.widget.RecyclerView
import com.example.finalplayground.R
import com.example.finalplayground.databinding.LayoutHeaderBinding
import com.example.finalplayground.domain.model.Account
import com.example.finalplayground.ui.common.formattedAmount
import com.example.finalplayground.ui.common.toAccessibleNumber

class HeaderAdapter(private val account: Account) : RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutHeaderBinding.inflate(inflater, parent, false)
        return HeaderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind(account)
    }

    override fun getItemCount(): Int {
        return 1
    }

    class HeaderViewHolder(private val binding: LayoutHeaderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(account: Account) {
            binding.accountBalanceAmount.text = account.available.formattedAmount(itemView.context)
            binding.balanceAmount.text = account.balance.formattedAmount(itemView.context)
            binding.pendingAmount.text = account.pendingBalance?.toString()?.formattedAmount(itemView.context)
            binding.accountDetails.text = SpannableStringBuilder()
                .append(itemView.context.getString(R.string.bsb_label))
                .color(ContextCompat.getColor(itemView.context, R.color.colorTitle)) {
                    append(account.bsb)
                    append("  ")
                }.append(itemView.context.getString(R.string.account_label))
                .color(ContextCompat.getColor(itemView.context, R.color.colorTitle)) {
                    append(account.accountNumber)
                }
            binding.accountDetails.contentDescription = itemView.context.getString(
                R.string.bsb_account_content_description,
                account.bsb.toAccessibleNumber(),
                account.accountNumber.toAccessibleNumber()
            )
        }
    }
}
