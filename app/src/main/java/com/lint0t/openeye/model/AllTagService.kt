package com.lint0t.openeye.model

import com.lint0t.openeye.bean.AllTagBean
import retrofit2.Call
import retrofit2.http.GET

interface AllTagService {
    @GET("v4/categories/all")
    fun getAllTag():Call<AllTagBean>
}