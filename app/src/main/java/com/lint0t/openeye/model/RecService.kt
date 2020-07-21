package com.lint0t.openeye.model

import com.lint0t.openeye.bean.RecBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface RecService {

    @GET("v5/index/tab/allRec")
    fun getRec(): Call<RecBean>

    @GET
    fun getMore(@Url url: String): Call<RecBean>

    @GET("v5/index/tab/feed?udid=20d2c76ac00b4b2ea4fe0249eafb6dc470d782a5&vc=6030022&vn=6.3.2&size=1080X1920&deviceModel=MI%205s%20Plus&first_channel=xiaomi&last_channel=xiaomi&system_version_code=26")
    fun getDaily(): Call<RecBean>
}