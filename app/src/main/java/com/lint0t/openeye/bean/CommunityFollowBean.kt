package com.lint0t.openeye.bean

data class CommunityFollowBean(
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
        val adTrack: List<Any>,
        val content: Content,
        val dataType: String,
        val header: Header
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

    data class DataX(
        val ad: Boolean,
        val adTrack: List<Any>,
        val author: Author,
        val category: String,
        val collected: Boolean,
        val consumption: Consumption,
        val cover: Cover,
        val dataType: String,
        val date: Long,
        val description: String,
        val descriptionEditor: String,
        val descriptionPgc: String,
        val duration: Int,
        val id: Int,
        val idx: Int,
        val ifLimitVideo: Boolean,
        val labelList: List<Any>,
        val library: String,
        val playInfo: List<PlayInfo>,
        val playUrl: String,
        val played: Boolean,
        val provider: Provider,
        val reallyCollected: Boolean,
        val releaseTime: Long,
        val remark: String,
        val resourceType: String,
        val searchWeight: Int,
        val subtitles: List<Any>,
        val tags: List<Tag>,
        val title: String,
        val titlePgc: String,
        val type: String,
        val webUrl: WebUrl
    )

    data class Author(
        val approvedNotReadyVideoCount: Int,
        val description: String,
        val expert: Boolean,
        val follow: Follow,
        val icon: String,
        val id: Int,
        val ifPgc: Boolean,
        val latestReleaseTime: Long,
        val link: String,
        val name: String,
        val recSort: Int,
        val shield: Shield,
        val videoNum: Int
    )

    data class Consumption(
        val collectionCount: Int,
        val realCollectionCount: Int,
        val replyCount: Int,
        val shareCount: Int
    )

    data class Cover(
        val blurred: String,
        val detail: String,
        val feed: String,
        val homepage: String
    )

    data class PlayInfo(
        val height: Int,
        val name: String,
        val type: String,
        val url: String,
        val urlList: List<Url>,
        val width: Int
    )

    data class Provider(
        val alias: String,
        val icon: String,
        val name: String
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

    data class WebUrl(
        val forWeibo: String,
        val raw: String
    )

    data class Follow(
        val followed: Boolean,
        val itemId: Int,
        val itemType: String
    )

    data class Shield(
        val itemId: Int,
        val itemType: String,
        val shielded: Boolean
    )

    data class Url(
        val name: String,
        val size: Int,
        val url: String
    )
}