package com.lint0t.openeye.model

import com.lint0t.openeye.bean.TagBean
import com.lint0t.openeye.bean.TagInfoBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface TagService {
    @GET("v6/tag/index")
    fun getTagInfo(@Query("id") id: String): Call<TagInfoBean>

    @GET("v1/tag/videos")
    fun getTagRec(
        @Query("id") id: String,
        @Query("udid") udid: String = "20d2c76ac00b4b2ea4fe0249eafb6dc470d782a5",
        @Query("vc") vc: String = "6030022",
        @Query("vn") vn: String = "6.3.2",
        @Query("size") size: String = "1080X1920",
        @Query("deviceModel") deviceModel: String = "MI%205s%20Plus",
        @Query("first_channel") first_channel: String = "xiaomi",
        @Query("last_channel") last_channel: String = "xiaomi",
        @Query("system_version_code") system_version_code: String = "26"
    ): Call<TagBean>

    @GET
    fun getMoreTagRec(@Url url: String): Call<TagBean>
}