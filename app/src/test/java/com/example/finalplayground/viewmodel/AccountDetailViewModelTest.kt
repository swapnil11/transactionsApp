package com.example.finalplayground.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.finalplayground.data.network.Resource
import com.example.finalplayground.domain.model.AccountDetail
import com.example.finalplayground.domain.model.Category
import com.example.finalplayground.domain.usecases.AccountDetailUseCase
import com.example.finalplayground.ui.viewmodel.AccountDetailViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class AccountDetailViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var accountDetailUseCase: AccountDetailUseCase

    private lateinit var accountDetailViewModel: AccountDetailViewModel

    var accountDetail = readJson("response.json") // Reading mock json

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        coEvery { accountDetailUseCase.accountDetail() } returns Resource.success(accountDetail)
        accountDetailViewModel = AccountDetailViewModel(accountDetailUseCase)
    }

    @Test
    fun testAccountDetailViewModel() {
        runBlocking {
            accountDetailViewModel.remoteAccountDetail.observeForever {
                if (it.status == Resource.Status.SUCCESS) {
                    Assert.assertEquals(accountDetail, it.data)
                }
            }
        }
    }

    @Test
    fun testDataTransformation() {
        val items = accountDetailViewModel.mapOfTransactions(accountDetail!!)
        Assert.assertEquals(items.size, 14)
        Assert.assertEquals(
            items.keys,
            setOf(
                LocalDate.parse("2021-02-28"),
                LocalDate.parse("2021-02-27"),
                LocalDate.parse("2021-02-26"),
                LocalDate.parse("2021-02-25"),
                LocalDate.parse("2021-02-24"),
                LocalDate.parse("2021-02-23"),
                LocalDate.parse("2021-02-22"),
                LocalDate.parse("2021-02-21"),
                LocalDate.parse("2021-02-20"),
                LocalDate.parse("2021-02-18"),
                LocalDate.parse("2021-02-17"),
                LocalDate.parse("2021-02-14"),
                LocalDate.parse("2021-02-13"),
                LocalDate.parse("2021-02-12")
            )
        )

        Assert.assertEquals(items.keys.first(), LocalDate.parse("2021-02-28"))
        val transactions = items.values.first()
        Assert.assertEquals(transactions.size, 3)
        val firstTransaction = transactions.first()
        Assert.assertEquals(firstTransaction.category, Category.SHOPPING)
        Assert.assertEquals(firstTransaction.amount, "-14.19")
        Assert.assertEquals(firstTransaction.description, "Setapp (via Paddle.Net) +440808178853 GBR")
        Assert.assertEquals(firstTransaction.atmId, null)
        Assert.assertEquals(firstTransaction.isPending, true)

        Assert.assertEquals(items.keys.last(), LocalDate.parse("2021-02-12"))
        Assert.assertEquals(items.values.last().size, 2)
        val lastTransaction = transactions.last()
        Assert.assertEquals(lastTransaction.category, Category.ENTERTAINMENT)
        Assert.assertEquals(lastTransaction.amount, "-6.99")
        Assert.assertEquals(lastTransaction.description, "Amazon Prime AU Membership Sydney South NSW")
        Assert.assertEquals(lastTransaction.atmId, null)
        Assert.assertEquals(lastTransaction.isPending, false)
    }

    private fun readJson(fileName: String): AccountDetail? {
        val input = this::class.java.classLoader?.getResourceAsStream(fileName) ?: return null
        return Json.decodeFromStream(input)
    }
}
