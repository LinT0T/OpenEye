package com.lint0t.openeye.bean

import java.io.Serializable

data class RecData(
    val text: String,
    val url: String,
    val playUrl: String,
    val time: Int,
    val title: String,
    val author: String,
    val description: String?,
    val id: String,
    val blurred: String,
    val avatar:String,
    val type: String,
    val nextUrl:String
) :Serializable{
}