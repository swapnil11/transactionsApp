package com.example.finalplayground.data

import com.example.finalplayground.BaseTest
import com.example.finalplayground.data.network.Resource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class AppRepositoryTest : BaseTest() {

    @Test
    fun testSuccessResponse() {
        setResponse("response.json")
        runBlocking {
            Assert.assertTrue(
                repository.accountDetail().status == Resource.Status.SUCCESS
            )
        }
    }

    @Test
    fun testFailResponse() {
        setErrorResponse()
        runBlocking {
            Assert.assertTrue(
                repository.accountDetail().status == Resource.Status.ERROR
            )
        }
    }

    @Test
    fun testTransactionItems() {
        setResponse("response.json")
        runBlocking {
            val expectedItems = 37 // in local json file, we have 37 transaction items.
            Assert.assertEquals(
                repository.accountDetail().data?.transactions?.size, expectedItems
            )
        }
    }

    @Test
    fun testInvalidDataResponse() {
        setEmptyResponse()
        runBlocking {
            val expectedItems = 0
            val response = repository.accountDetail()
            Assert.assertTrue(
                response.status == Resource.Status.ERROR
            )
        }
    }
}
