package com.lint0t.openeye.model

import com.lint0t.openeye.bean.RecBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("v3/search")
    fun getSearch(@Query("query") query: String): Call<RecBean>
}