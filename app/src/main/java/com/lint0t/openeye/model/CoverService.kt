package com.lint0t.openeye.model

import com.lint0t.openeye.bean.WelcomeBean
import retrofit2.Call
import retrofit2.http.GET

interface CoverService {
    @GET("HPImageArchive.aspx?format=js&idx=0&n=1")
    fun getImageData(): Call<WelcomeBean>
}