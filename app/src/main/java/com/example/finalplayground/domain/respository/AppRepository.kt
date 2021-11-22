package com.example.finalplayground.domain.respository

import com.example.finalplayground.data.network.Resource
import com.example.finalplayground.domain.model.AccountDetail

/**
 * Repository layer for fetching data from either network or db layer.
 */
interface AppRepository {
    /**
     * Fetches the account detail from the remote API.
     */
    suspend fun accountDetail(): Resource<AccountDetail?>
}
