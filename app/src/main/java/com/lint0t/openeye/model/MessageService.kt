package com.lint0t.openeye.model

import com.lint0t.openeye.bean.MessageBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface MessageService {
    @GET("v3/messages?udid=20d2c76ac00b4b2ea4fe0249eafb6dc470d782a5&vc=6030022&vn=6.3.2&size=1080X1920&deviceModel=MI%205s%20Plus&first_channel=xiaomi&last_channel=xiaomi&system_version_code=26")
    fun getMessage(): Call<MessageBean>

    @GET
    fun getMoreMessage(@Url url: String): Call<MessageBean>
}