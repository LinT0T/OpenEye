package com.lint0t.openeye.bean

data class ReplyBean(
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
        val tag: Any,
        val type: String
    )

    data class Data(
        val actionUrl: String,
        val adTrack: Any,
        val cover: Any,
        val createTime: Long,
        val dataType: String,
        val font: String,
        val hot: Boolean,
        val id: Long,
        val imageUrl: String,
        val likeCount: Int,
        val liked: Boolean,
        val message: String,
        val parentReply: Any,
        val parentReplyId: Long,
        val replyStatus: String,
        val rootReplyId: Long,
        val sequence: Int,
        val showConversationButton: Boolean,
        val showParentReply: Boolean,
        val sid: String,
        val text: String,
        val type: String,
        val ugcVideoId: Any,
        val ugcVideoUrl: Any,
        val user: User,
        val userBlocked: Boolean,
        val userType: Any,
        val videoId: Int,
        val videoTitle: String
    )

    data class User(
        val actionUrl: String,
        val area: Any,
        val avatar: String,
        val birthday: Long,
        val city: String,
        val country: String,
        val cover: Any,
        val description: String,
        val expert: Boolean,
        val followed: Boolean,
        val gender: Any,
        val ifPgc: Boolean,
        val job: String,
        val library: String,
        val limitVideoOpen: Boolean,
        val nickname: String,
        val registDate: Long,
        val releaseDate: Any,
        val uid: Int,
        val university: String,
        val userType: String
    )
}