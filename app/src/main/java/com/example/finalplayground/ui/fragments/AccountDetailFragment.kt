package com.example.finalplayground.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.finalplayground.R
import com.example.finalplayground.data.network.Resource
import com.example.finalplayground.databinding.FragmentAccountDetailBinding
import com.example.finalplayground.domain.model.AccountDetail
import com.example.finalplayground.ui.adapters.AccountDetailRecyclerViewAdapter
import com.example.finalplayground.ui.adapters.HeaderAdapter
import com.example.finalplayground.ui.adapters.ItemClickListener
import com.example.finalplayground.ui.common.TransactionItem
import com.example.finalplayground.ui.common.showErrorBar
import com.example.finalplayground.ui.viewmodel.AccountDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountDetailFragment : Fragment(), ItemClickListener {
    private val viewModel: AccountDetailViewModel by hiltNavGraphViewModels(R.id.accountDetailFragment)
    private lateinit var binding: FragmentAccountDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAccountDetailBinding.inflate(inflater, container, false).apply {
            binding = this
            lifecycleOwner = this@AccountDetailFragment
            vm = this@AccountDetailFragment.viewModel
            clickListener = this@AccountDetailFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeAccountDetail()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                viewModel.refresh()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeAccountDetail() {
        viewModel.remoteAccountDetail.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.isLoading = true
                    binding.isEmpty = false
                }
                Resource.Status.SUCCESS -> handleSuccessResponse(data = it.data)
                Resource.Status.ERROR -> handleErrorResponse(msg = it.message)
            }
        }
    }

    private fun handleSuccessResponse(data: AccountDetail? = null) {
        binding.isLoading = false
        data?.run {
            requireActivity().title = account.accountName
            binding.isEmpty = transactions.isEmpty()
            // Using the new Concat Adapter to render the header view and
            // date,transactions as part of recyclerview itself.
            val adapter = ConcatAdapter(
                HeaderAdapter(account),
                AccountDetailRecyclerViewAdapter(
                    data,
                    viewModel.mapOfTransactions(data),
                    this@AccountDetailFragment
                )
            )
            binding.transactionsList.adapter = adapter
        }
    }

    private fun handleErrorResponse(msg: String? = null) {
        binding.isLoading = false
        binding.isEmpty = true
        showErrorBar(msg)
    }

    override fun onItemClick(item: Any?) {
        if (item is TransactionItem) {
            findNavController().navigate(AccountDetailFragmentDirections.listFragmentAction(requireActivity().title.toString(), item.transaction, item.atm))
        }
    }
}
