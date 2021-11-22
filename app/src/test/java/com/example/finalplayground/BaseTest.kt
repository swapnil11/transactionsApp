package com.example.finalplayground

import com.example.finalplayground.data.AppRepositoryImpl
import com.example.finalplayground.data.network.BankingApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
// import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit

abstract class BaseTest {

    var mockWebServer = MockWebServer()

    @ExperimentalSerializationApi
    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("").toString())
        .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
        .build()
        .create(BankingApi::class.java)

    @ExperimentalSerializationApi
    var repository = AppRepositoryImpl(api)

    fun setResponse(fileName: String) {
        val input = this.javaClass.classLoader?.getResourceAsStream(fileName)
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(input?.bufferedReader().use { it!!.readText() })
        )
    }

    fun setEmptyResponse() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody("[]"))
    }

    fun setErrorResponse() {
        mockWebServer.enqueue(MockResponse().setResponseCode(400).setBody("{}"))
    }
}
