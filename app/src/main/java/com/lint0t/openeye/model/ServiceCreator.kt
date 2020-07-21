package com.lint0t.openeye.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    // private const val BASE_URL = "http//cn.bing.com/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http//cn.bing.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> createService(serviceClass: Class<T>): T {

        return retrofit.create(serviceClass)
    }

    inline fun <reified T> create(): T = createService(T::class.java)

}