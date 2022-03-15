package com.smh.githubfollowercompose.data.remote.api

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source

open class FakeApi {
    val mockWebServer = MockWebServer()

    fun enqueue200(filename : String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(filename)
        val buffer = inputStream.source().buffer()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(buffer.readString(Charsets.UTF_8))
        )
    }
}