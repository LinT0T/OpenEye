package com.lint0t.openeye.bean

data class RecBean(
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
        val actionUrl: String,
        val ad: Boolean,
        val adTrack: List<Any>,
        val author: Author,
        val autoPlay: Boolean,
        val category: String,
        val collected: Boolean,
        val consumption: Consumption,
        val content: Content,
        val count: Int,
        val cover: CoverX,
        val dataType: String,
        val date: Long,
        val description: String?,
        val descriptionEditor: String,
        val descriptionPgc: String,
        val duration: Int,
        val header: Header,
        val icon:String,
        val id: Int,
        val idx: Int,
        val ifLimitVideo: Boolean,
        val image: String,
        val itemList: List<ItemX>,
        val label: Label,
        val labelList: List<Any>,
        val library: String,
        val playInfo: List<PlayInfoX>,
        val playUrl: String,
        val played: Boolean,
        val provider: ProviderXX,
        val reallyCollected: Boolean,
        val recallSource: String,
        val releaseTime: Long,
        val resourceType: String,
        val searchWeight: Int,
        val shade: Boolean,
        val slogan: String,
        val src: Int,
        val subtitles: List<Any>,
        val tags: List<TagXX>,
        val text: String,
        val thumbPlayUrl: String,
        val title: String,
        val titlePgc: String,
        val type: String,
        val webUrl: WebUrlXX
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

    data class Content(
        val adIndex: Int,
        val `data`: DataX,
        val id: Int,
        val type: String
    )

    data class CoverX(
        val blurred: String,
        val detail: String,
        val feed: String,
        val homepage: String
    )

    data class Header(
        val actionUrl: String,
        val description: String,
        val font: String,
        val icon: String,
        val iconType: String,
        val id: Int,
        val rightText: String,
        val showHateVideo: Boolean,
        val subTitle: String,
        val subTitleFont: String,
        val textAlign: String,
        val time: Long,
        val title: String
    )

    data class ItemX(
        val adIndex: Int,
        val `data`: DataXX,
        val id: Int,
        val type: String
    )

    data class Label(
        val card: String,
        val text: String
    )

    data class PlayInfoX(
        val height: Int,
        val name: String,
        val type: String,
        val url: String,
        val urlList: List<UrlX>,
        val width: Int
    )

    data class ProviderXX(
        val alias: String,
        val icon: String,
        val name: String
    )

    data class TagXX(
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

    data class WebUrlXX(
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

    data class DataX(
        val ad: Boolean,
        val adTrack: List<Any>,
        val author: AuthorX,
        val category: String,
        val collected: Boolean,
        val consumption: ConsumptionX,
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
        val playInfo: List<Any>,
        val playUrl: String,
        val played: Boolean,
        val provider: Provider,
        val reallyCollected: Boolean,
        val recallSource: String,
        val releaseTime: Long,
        val resourceType: String,
        val searchWeight: Int,
        val slogan: String,
        val src: Int,
        val subtitles: List<Any>,
        val tags: List<Tag>,
        val thumbPlayUrl: String,
        val title: String,
        val titlePgc: String,
        val type: String,
        val webUrl: WebUrl
    )

    data class AuthorX(
        val approvedNotReadyVideoCount: Int,
        val description: String,
        val expert: Boolean,
        val follow: FollowX,
        val icon: String,
        val id: Int,
        val ifPgc: Boolean,
        val latestReleaseTime: Long,
        val link: String,
        val name: String?,
        val recSort: Int,
        val shield: ShieldX,
        val videoNum: Int
    )

    data class ConsumptionX(
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

    data class FollowX(
        val followed: Boolean,
        val itemId: Int,
        val itemType: String
    )

    data class ShieldX(
        val itemId: Int,
        val itemType: String,
        val shielded: Boolean
    )

    data class DataXX(
        val adTrack: List<Any>,
        val content: ContentX,
        val dataType: String,
        val header: HeaderX
    )

    data class ContentX(
        val adIndex: Int,
        val `data`: DataXXX,
        val id: Int,
        val type: String
    )

    data class HeaderX(
        val actionUrl: String,
        val icon: String,
        val iconType: String,
        val id: Int,
        val showHateVideo: Boolean,
        val textAlign: String,
        val time: Long,
        val title: String
    )

    data class DataXXX(
        val ad: Boolean,
        val adTrack: List<Any>,
        val author: AuthorXX,
        val category: String,
        val collected: Boolean,
        val consumption: ConsumptionXX,
        val cover: CoverXX,
        val dataType: String,
        val date: Long,
        val description: String,
        val descriptionEditor: String,
        val duration: Int,
        val id: Int,
        val idx: Int,
        val ifLimitVideo: Boolean,
        val labelList: List<Any>,
        val library: String,
        val playInfo: List<PlayInfo>,
        val playUrl: String,
        val played: Boolean,
        val provider: ProviderX,
        val reallyCollected: Boolean,
        val releaseTime: Long,
        val resourceType: String,
        val searchWeight: Int,
        val subtitles: List<Any>,
        val tags: List<TagX>,
        val title: String,
        val type: String,
        val webUrl: WebUrlX
    )

    data class AuthorXX(
        val approvedNotReadyVideoCount: Int,
        val description: String,
        val expert: Boolean,
        val follow: FollowXX,
        val icon: String,
        val id: Int,
        val ifPgc: Boolean,
        val latestReleaseTime: Long,
        val link: String,
        val name: String,
        val recSort: Int,
        val shield: ShieldXX,
        val videoNum: Int
    )

    data class ConsumptionXX(
        val collectionCount: Int,
        val realCollectionCount: Int,
        val replyCount: Int,
        val shareCount: Int
    )

    data class CoverXX(
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

    data class ProviderX(
        val alias: String,
        val icon: String,
        val name: String
    )

    data class TagX(
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

    data class WebUrlX(
        val forWeibo: String,
        val raw: String
    )

    data class FollowXX(
        val followed: Boolean,
        val itemId: Int,
        val itemType: String
    )

    data class ShieldXX(
        val itemId: Int,
        val itemType: String,
        val shielded: Boolean
    )

    data class Url(
        val name: String,
        val size: Int,
        val url: String
    )

    data class UrlX(
        val name: String,
        val size: Int,
        val url: String
    )
}