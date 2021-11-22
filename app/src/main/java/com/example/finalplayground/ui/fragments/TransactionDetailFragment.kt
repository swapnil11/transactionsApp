package com.example.finalplayground.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.finalplayground.databinding.FragmentTransactionDetailBinding
import com.example.finalplayground.ui.common.toDateContentHolder
import com.example.finalplayground.ui.common.toSentenceCase

class TransactionDetailFragment : Fragment() {
    private lateinit var binding: FragmentTransactionDetailBinding
    private val args by navArgs<TransactionDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentTransactionDetailBinding.inflate(inflater, container, false).apply {
            binding = this
            lifecycleOwner = this@TransactionDetailFragment
            transaction = args.transaction
            atm = args.atm
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = args.screenTitle
        binding.transactionDescription.text = HtmlCompat.fromHtml(args.transaction.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.transactionCategory.text = args.transaction.category.name.toSentenceCase().replace("_", " ")
        binding.transactionDate.text = args.transaction.effectiveDate.toDateContentHolder(requireContext()).spannableStringBuilder
        binding.transactionDate.contentDescription = args.transaction.effectiveDate.toDateContentHolder(requireContext()).contentDescription
    }
}
