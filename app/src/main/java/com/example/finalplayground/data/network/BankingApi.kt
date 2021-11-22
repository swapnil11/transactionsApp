package com.example.finalplayground.data.network

import com.example.finalplayground.domain.model.AccountDetail
import retrofit2.http.GET

/**
 * API class for defining endpoint and request for the application
 */
interface BankingApi {
    /**
     * Suspend function which fetches the [List] of [AccountDetail] from the [ACCOUNT_DETAIL_ENDPOINT].
     * @return list of [AccountDetail]
     */
    @GET(ACCOUNT_DETAIL_ENDPOINT)
    suspend fun accountDetail(): AccountDetail?

    companion object {
        const val BASE_URL = "https://gist.githubusercontent.com/swapnil11/"
        private const val ACCOUNT_DETAIL_ENDPOINT = "4d9db2f8da6a039ac34d0c22cadb4a7c/raw/0a1813cd74281aed7c23eb1a34a0870ed6b7dd57/transaction.json"
    }
}
