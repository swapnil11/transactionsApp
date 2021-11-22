package com.example.finalplayground.data

import com.example.finalplayground.data.network.BankingApi
import com.example.finalplayground.data.network.Resource
import com.example.finalplayground.data.network.ResponseHandler
import com.example.finalplayground.domain.model.AccountDetail
import com.example.finalplayground.domain.respository.AppRepository
import javax.inject.Inject

/**
 * Repository layer for fetching data from either network or db layer.
 */
class AppRepositoryImpl @Inject constructor(
    private val api: BankingApi
) : AppRepository {

    /**
     * Fetches the account details from the remote API.
     */
    override suspend fun accountDetail(): Resource<AccountDetail?> = try {
        ResponseHandler.handleSuccess(api.accountDetail())
    } catch (e: Exception) {
        ResponseHandler.handleException(e)
    }
}
