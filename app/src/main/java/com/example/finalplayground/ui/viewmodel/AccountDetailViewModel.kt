package com.example.finalplayground.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.finalplayground.data.network.Resource
import com.example.finalplayground.domain.model.AccountDetail
import com.example.finalplayground.domain.usecases.AccountDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountDetailViewModel @Inject constructor(private val useCase: AccountDetailUseCase) : ViewModel() {

    private val accountDetailLiveData = MutableLiveData(Unit)

    var remoteAccountDetail = accountDetailLiveData.switchMap {
        fetchAccountDetail()
    }

    fun refresh() {
        accountDetailLiveData.value = Unit
    }

    private fun fetchAccountDetail() = liveData {
        emit(Resource.loading())
        emit(useCase.accountDetail())
    }

    fun mapOfTransactions(accountDetail: AccountDetail) = accountDetail.run {
        transactions.sortedByDescending { it.effectiveDate }
            .groupBy({ it.effectiveDate }, { it })
    }
}
