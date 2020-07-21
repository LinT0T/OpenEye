package com.lint0t.openeye.bean

data class CommunityRecBean(
    val adExist: Boolean,
    val count: Int,
    val itemList: List<Item>,
    val nextPageUrl: String,
    val total: Int
) {

    data class Item(
        val adIndex: Int,
        val `data`: Data,
        val id: Int,
        val type: String
    )

    data class Data(
        val content: Content,
        val count: Int,
        val dataType: String,
        val header: Header,
        val itemList: List<ItemX>
    )

    data class Content(
        val adIndex: Int,
        val `data`: DataX,
        val id: Int,
        val type: String
    )

    data class Header(
        val actionUrl: String,
        val followType: String,
        val icon: String,
        val iconType: String,
        val id: Int,
        val issuerName: String,
        val showHateVideo: Boolean,
        val tagId: Int,
        val time: Long,
        val topShow: Boolean
    )

    data class ItemX(
        val adIndex: Int,
        val `data`: DataXX,
        val id: Int,
        val type: String
    )

    data class DataX(
        val addWatermark: Boolean,
        val area: String,
        val checkStatus: String,
        val city: String,
        val collected: Boolean,
        val consumption: Consumption,
        val cover: Cover,
        val createTime: Long,
        val dataType: String,
        val description: String,
        val duration: Int,
        val height: Int,
        val id: Int,
        val ifMock: Boolean,
        val latitude: Double,
        val library: String,
        val longitude: Double,
        val owner: Owner,
        val playUrl: String,
        val playUrlWatermark: String,
        val privateMessageActionUrl: String,
        val reallyCollected: Boolean,
        val recentOnceReply: RecentOnceReply,
        val releaseTime: Long,
        val resourceType: String,
        val selectedTime: Long,
        val status: Any,
        val tags: List<Tag>,
        val title: String,
        val type: String,
        val uid: Int,
        val updateTime: Long,
        val url: String,
        val urls: List<String>,
        val urlsWithWatermark: List<String>,
        val validateResult: String,
        val validateStatus: String,
        val validateTaskId: String,
        val width: Int
    )

    data class Consumption(
        val collectionCount: Int,
        val realCollectionCount: Int,
        val replyCount: Int,
        val shareCount: Int
    )

    data class Cover(
        val detail: String,
        val feed: String
    )

    data class Owner(
        val actionUrl: String,
        val avatar: String,
        val birthday: Long,
        val city: String,
        val country: String,
        val cover: String,
        val description: String,
        val expert: Boolean,
        val followed: Boolean,
        val gender: String,
        val ifPgc: Boolean,
        val job: String,
        val library: String,
        val limitVideoOpen: Boolean,
        val nickname: String,
        val registDate: Long,
        val releaseDate: Long,
        val uid: Int,
        val university: String,
        val userType: String
    )

    data class RecentOnceReply(
        val actionUrl: String,
        val dataType: String,
        val message: String,
        val nickname: String
    )

    data class Tag(
        val actionUrl: String,
        val bgPicture: String,
        val communityIndex: Int,
        val desc: String,
        val haveReward: Boolean,
        val headerImage: String,
        val id: Int,
        val ifNewest: Boolean,
        val name: String,
        val tagRecType: String
    )

    data class DataXX(
        val actionUrl: String,
        val adTrack: List<Any>,
        val autoPlay: Boolean,
        val bgPicture: String,
        val dataType: String,
        val description: String,
        val header: HeaderX,
        val id: Int,
        val image: String,
        val label: Label,
        val labelList: List<Any>,
        val shade: Boolean,
        val subTitle: String,
        val title: String
    )

    data class HeaderX(
        val id: Int,
        val textAlign: String
    )

    data class Label(
        val card: String,
        val text: String
    )
}