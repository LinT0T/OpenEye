package com.lint0t.openeye.bean

data class CommunityFollowData(
    val url: String,
    val playUrl: String,
    val time: Int,
    val createTime:Long,
    val title: String,
    val author: String,
    val description: String?,
    val id: String,
    val blurred: String,
    val avatar: String,
    val good:Int,
    val message:Int,
    val shareCount:Int,
    val type: String,
    val nextUrl: String
) {
}