package com.lint0t.openeye.bean

data class TagRecData(
    val text: String,
    val url: String,
    val playUrl: String,
    val time: Int,
    val title: String,
    val author: String,
    val description: String?,
    val id: String,
    val blurred: String,
    val avatar: String,
    val type: String,
    val nextUrl: String,
    val good:Int,
    val message:Int,
    val shareCount:Int,
    val createTime:Long
) {
}