package com.lint0t.openeye.bean

data class TagInfoBean(
    val tabInfo: TabInfo,
    val tagInfo: TagInfo
) {
    data class TabInfo(
        val defaultIdx: Int,
        val tabList: List<Tab>
    )

    data class TagInfo(
        val actionUrl: Any,
        val bgPicture: String,
        val childTabList: List<Any>,
        val dataType: String,
        val description: String?,
        val follow: Follow,
        val headerImage: String,
        val id: Int,
        val lookCount: Int,
        val name: String,
        val recType: Int,
        val shareLinkUrl: String,
        val tagDynamicCount: Int,
        val tagFollowCount: Int,
        val tagVideoCount: Int
    )

    data class Tab(
        val adTrack: Any,
        val apiUrl: String,
        val id: Int,
        val name: String,
        val nameType: Int,
        val tabType: Int
    )

    data class Follow(
        val followed: Boolean,
        val itemId: Int,
        val itemType: String
    )
}