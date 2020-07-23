package com.lint0t.openeye.model

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


object OpenEyeNetwork {
    private val retrofitImage = Retrofit.Builder().baseUrl("http://cn.bing.com/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    private val coverService = retrofitImage.create(CoverService::class.java)

    //    private val appService = ServiceCreator.createService(AppService::class.java) //用retrofit构建器构建会有奇怪的错误，不知道为什么
    private val retrofit = Retrofit.Builder().baseUrl(" http://baobab.kaiyanapp.com/api/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    private val discoveryService = retrofit.create(DiscoveryService::class.java)
    private val playVideoService = retrofit.create(PlayVideoService::class.java)
    private val recService = retrofit.create(RecService::class.java)
    private val searchService = retrofit.create(SearchService::class.java)
    private val communityService = retrofit.create(CommunityService::class.java)
    private val messageService = retrofit.create(MessageService::class.java)
    private val tagService = retrofit.create(TagService::class.java)
    private val allTagService = retrofit.create(AllTagService::class.java)
    private val calenderService = retrofit.create(CalenderService::class.java)

    suspend fun loadImage() = coverService.getImageData().await()

    suspend fun loadDiscovery() = discoveryService.getDiscovery().await()

    suspend fun loadRelated(query: String) = playVideoService.getRelated(query).await()

    suspend fun loadReply(query: String) = playVideoService.getReply(query).await()

    suspend fun loadRec() = recService.getRec().await()

    suspend fun loadMore(url: String) = recService.getMore(url).await()

    suspend fun loadSearch(query: String) = searchService.getSearch(query).await()

    suspend fun loadCommunity() = communityService.getCommunity().await()

    suspend fun loadMoreCommunityRec(url: String) =
        communityService.getMoreCommunityRec(url).await()

    suspend fun loadDaily() = recService.getDaily().await()

    suspend fun loadCommunityFollow() = communityService.getCommunityFollow().await()

    suspend fun loadMessage() = messageService.getMessage().await()

    suspend fun loadMoreMessage(url: String) = messageService.getMoreMessage(url).await()

    suspend fun loadTagRec(id: String) = tagService.getTagRec(id).await()

    suspend fun loadMoreTagRec(url: String) = tagService.getMoreTagRec(url).await()

    suspend fun loadTagInfo(id: String) = tagService.getTagInfo(id).await()

    suspend fun loadAllTag() = allTagService.getAllTag().await()

    suspend fun loadCalender(date:String) = calenderService.getCalender(date).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response is null")
                    )
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    Log.e("Internet error:", t.toString())
                    continuation.resumeWithException(t)
                }
            }
            )
        }
    }

}