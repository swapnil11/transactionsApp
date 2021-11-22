package com.example.finalplayground.domain.usecases

import com.example.finalplayground.domain.respository.AppRepository
import javax.inject.Inject

/**
 * Usecase layer responsible for fetching data from the repository layer.
 *
 * @param repository DI injected repository
 */
class AccountDetailUseCase @Inject constructor(private val repository: AppRepository) {
    /**
     * Fetches a list of account detail from the repository layer.
     */
    suspend fun accountDetail() = repository.accountDetail()
}
