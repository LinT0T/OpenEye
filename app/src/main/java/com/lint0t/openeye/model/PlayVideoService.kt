package com.lint0t.openeye.model

import com.lint0t.openeye.bean.RelatedBean
import com.lint0t.openeye.bean.ReplyBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlayVideoService {
    @GET("v4/video/related")
    fun getRelated(@Query("id") id: String): Call<RelatedBean>

    @GET("v2/replies/video")
    fun getReply(@Query("videoId") videoId: String): Call<ReplyBean>
}