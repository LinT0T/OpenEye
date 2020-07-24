package com.lint0t.openeye.bean

data class AllTagBean(
    val adExist: Boolean,
    val count: Int,
    val itemList: List<Item>,
    val nextPageUrl: Any,
    val total: Int
) {
    data class Item(
        val adIndex: Int,
        val `data`: Data,
        val id: Int,
        val tag: Any,
        val type: String
    )

    data class Data(
        val actionUrl: String,
        val adTrack: Any,
        val dataType: String,
        val description: Any,
        val id: Int,
        val image: String,
        val shade: Boolean,
        val title: String
    )
}