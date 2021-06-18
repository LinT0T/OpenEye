package com.lint0t.openeye.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    private const val IMAGE = "http://cn.bing.com/"
    private const val BASE = "http://baobab.kaiyanapp.com/api/"

    private val retrofitImage: Retrofit = Retrofit.Builder()
        .baseUrl(IMAGE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> createImgService(serviceClass: Class<T>): T {
        return retrofitImage.create(serviceClass)
    }

    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }

    inline fun <reified T> createImg(): T = createImgService(T::class.java)
    inline fun <reified T> create(): T = createService(T::class.java)


}