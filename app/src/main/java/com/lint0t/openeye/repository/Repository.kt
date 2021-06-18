package com.lint0t.openeye.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.lint0t.openeye.bean.*
import com.lint0t.openeye.model.OpenEyeNetwork
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object Repository {
    fun loadImage() = fire(Dispatchers.IO) {
        val imageResponse = OpenEyeNetwork.loadImage()
        val url = imageResponse.images[0].url
        val text = imageResponse.images[0].copyright
        val imageData = ImageData(url, text)
        Result.success(imageData)

    }


    fun loadBanner() = fire(Dispatchers.IO) {
        val response = OpenEyeNetwork.loadDiscovery()
        val length = response.itemList[0].data.count
        val list = mutableListOf<String>()
        for (i in 0 until length) {
            list.add(response.itemList[0].data.itemList[i].data.image)
        }

        Result.success(list)

    }

    fun loadSpecial() = fire(Dispatchers.IO) {
        val response = OpenEyeNetwork.loadDiscovery()
        val len = response.count
        val list = mutableListOf<SpecialData>()
        for (j in 0 until len) {
            if (response.itemList[j].type == "specialSquareCardCollection") {
                val length = response.itemList[j].data.count
                for (i in 0 until length) {
                    val specialData = SpecialData(
                        response.itemList[j].data.itemList[i].data.image,
                        response.itemList[j].data.itemList[i].data.title,
                        response.itemList[j].data.itemList[i].data.id.toString()
                    )
                    list.add(specialData)
                }
                break
            }
        }


        Result.success(list)
    }

    fun loadColumn() = fire(Dispatchers.IO) {
        val response = OpenEyeNetwork.loadDiscovery()
        val length = response.itemList[2].data.count
        val list = mutableListOf<ColumnData>()
        for (i in 0 until length) {
            val columnData = ColumnData(
                response.itemList[2].data.itemList[i].data.image,
                response.itemList[2].data.itemList[i].data.title
            )
            list.add(columnData)
        }

        Result.success(list)
    }

    fun loadTop() = fire(Dispatchers.IO) {
        val response = OpenEyeNetwork.loadDiscovery()
        val length = response.count
        val list = mutableListOf<TopData>()
        for (i in 0 until length) {
            if (response.itemList[i].type == "videoSmallCard") {
                val topData = TopData(
                    response.itemList[i].data.cover.feed,
                    response.itemList[i].data.playUrl,
                    response.itemList[i].data.duration,
                    response.itemList[i].data.title,
                    response.itemList[i].data.category,
                    response.itemList[i].data.description,
                    response.itemList[i].data.id.toString(),
                    response.itemList[i].data.cover.blurred
                )
                list.add(topData)
            }
        }

        Result.success(list)
    }


    fun loadTopic() = fire(Dispatchers.IO) {
        val response = OpenEyeNetwork.loadDiscovery()
        val length = response.count
        val list = mutableListOf<TopicData>()
        for (i in 0 until length) {
            if (response.itemList[i].type == "briefCard") {
                val topicData: TopicData = TopicData(
                    response.itemList[i].data.icon,
                    response.itemList[i].data.title,
                    response.itemList[i].data.description
                )

                list.add(topicData)
            }
        }
        Result.success(list)
    }

    fun loadRelated(query: String) = fire(Dispatchers.IO) {
        val response = OpenEyeNetwork.loadRelated(query)
        val length = response.count
        val list = mutableListOf<TopData>()
        for (i in 0 until length) {
            if (response.itemList[i].type == "videoSmallCard") {

                val relatedData = TopData(
                    response.itemList[i].data.cover.feed,
                    response.itemList[i].data.playUrl,
                    response.itemList[i].data.duration,
                    response.itemList[i].data.title,
                    response.itemList[i].data.category,
                    response.itemList[i].data.description,
                    response.itemList[i].data.id.toString(),
                    response.itemList[i].data.cover.blurred
                )
                list.add(relatedData)
            }
        }

        Result.success(list)
    }

    fun loadReply(query: String) = fire(Dispatchers.IO) {
        val response = OpenEyeNetwork.loadReply(query)
        val length = response.count
        val list = mutableListOf<ReplyData>()
        for (i in 0 until length) {
            if (response.itemList[i].type == "leftAlignTextHeader") {
                val replyData =
                    ReplyData(
                        "",
                        "",
                        "",
                        0,
                        0,
                        response.itemList[i].data.text,
                        response.itemList[i].type
                    )
                list.add(replyData)
            }
            if (response.itemList[i].type == "reply") {
                val replyData =
                    ReplyData(
                        response.itemList[i].data.user.avatar,
                        response.itemList[i].data.user.nickname,
                        response.itemList[i].data.message,
                        response.itemList[i].data.createTime,
                        response.itemList[i].data.likeCount,
                        "",
                        response.itemList[i].type
                    )
                list.add(replyData)
            }
        }

        Result.success(list)
    }

    fun loadMore(url: String) = fire(Dispatchers.IO) {
        val response = OpenEyeNetwork.loadMore(url)
        val length = response.count
        val list = mutableListOf<RecData>()
        for (i in 0 until length) {
            if (response.itemList[i].type == "squareCardCollection") {
                val s = response.itemList[i].data
                val len = s.count
                for (j in 0 until len) {
                    try {
                        val recData = RecData(
                            response.itemList[i].data.header.title,
                            s.itemList[j].data.content.data.cover.feed,
                            s.itemList[j].data.content.data.playUrl,
                            s.itemList[j].data.content.data.duration,
                            s.itemList[j].data.content.data.title,
                            "${s.itemList[j].data.content.data.author.name} / #${s.itemList[j].data.content.data.category}",
                            s.itemList[j].data.content.data.description,
                            s.itemList[j].data.content.data.id.toString(),
                            s.itemList[j].data.content.data.cover.blurred,
                            s.itemList[j].data.content.data.author.icon,
                            "squareCardCollection",
                            response.nextPageUrl
                        )
                        list.add(recData)
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }

                }
            }
            if (response.itemList[i].type == "textCard") {
                val recData = RecData(
                    response.itemList[i].data.text,
                    "", "", 0, "", "", "", "", "", "", response.itemList[i].type,
                    response.nextPageUrl
                )
                list.add(recData)
            }
            if (response.itemList[i].type == "followCard") {
                val recData = RecData(
                    "",
                    response.itemList[i].data.content.data.cover.feed,
                    response.itemList[i].data.content.data.playUrl,
                    response.itemList[i].data.content.data.duration,
                    response.itemList[i].data.content.data.title,
                    "${response.itemList[i].data.content.data.author.name} / #${response.itemList[i].data.content.data.category}",
                    response.itemList[i].data.content.data.description,
                    response.itemList[i].data.content.data.id.toString(),
                    response.itemList[i].data.content.data.cover.blurred,
                    response.itemList[i].data.content.data.author.icon,
                    response.itemList[i].type,
                    response.nextPageUrl
                )
                list.add(recData)
            }
            if (response.itemList[i].type == "videoSmallCard") {
                val recData = RecData(
                    "",
                    response.itemList[i].data.cover.feed,
                    response.itemList[i].data.playUrl,
                    response.itemList[i].data.duration,
                    response.itemList[i].data.title,
                    "${response.itemList[i].data.author.name} / #${response.itemList[i].data.category}",
                    response.itemList[i].data.description,
                    response.itemList[i].data.id.toString(),
                    response.itemList[i].data.cover.blurred,
                    response.itemList[i].data.author.icon,
                    response.itemList[i].type,
                    response.nextPageUrl
                )
                list.add(recData)
            }
        }

        Result.success(list)
    }

    fun loadRec() = fire(Dispatchers.IO) {


        val response = OpenEyeNetwork.loadRec()
        val length = response.count
        val list = mutableListOf<RecData>()
        try {
            for (i in 0 until length) {
                if (response.itemList[i].type == "squareCardCollection") {
                    val s = response.itemList[i].data
                    val len = s.count
                    for (j in 0 until len) {
                        try {
                            val recData = RecData(
                                response.itemList[i].data.header.title,
                                s.itemList[j].data.content.data.cover.feed,
                                s.itemList[j].data.content.data.playUrl,
                                s.itemList[j].data.content.data.duration,
                                s.itemList[j].data.content.data.title,
                                "${s.itemList[j].data.content.data.author.name} / #${s.itemList[j].data.content.data.category}",
                                s.itemList[j].data.content.data.description,
                                s.itemList[j].data.content.data.id.toString(),
                                s.itemList[j].data.content.data.cover.blurred,
                                s.itemList[j].data.content.data.author.icon,
                                "squareCardCollection",
                                response.nextPageUrl
                            )
                            list.add(recData)
                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                        }

                    }
                }
                if (response.itemList[i].type == "textCard" && response.itemList[i].data.type != "footer2" && length > 1) {
                    val recData = RecData(
                        response.itemList[i].data.text,
                        "", "", 0, "", "", "", "", "", "", response.itemList[i].type,
                        response.nextPageUrl
                    )
                    list.add(recData)
                }

                if (response.itemList[i].type == "banner2") {
                    val recData = RecData(
                        "",
                        response.itemList[i].data.image,
                        "",
                        0,
                        response.itemList[i].data.header.title,
                        "广告",
                        response.itemList[i].data.header.description,
                        "",
                        "",
                        response.itemList[i].data.header.icon,
                        "squareCardCollection",
                        response.nextPageUrl
                    )
                    list.add(recData)
                }
                if (response.itemList[i].type == "followCard") {
                    val recData = RecData(
                        "",
                        response.itemList[i].data.content.data.cover.feed,
                        response.itemList[i].data.content.data.playUrl,
                        response.itemList[i].data.content.data.duration,
                        response.itemList[i].data.content.data.title,
                        "${response.itemList[i].data.content.data.author.name} / #${response.itemList[i].data.content.data.category}",
                        response.itemList[i].data.content.data.description,
                        response.itemList[i].data.content.data.id.toString(),
                        response.itemList[i].data.content.data.cover.blurred,
                        response.itemList[i].data.content.data.author.icon,
                        response.itemList[i].type,
                        response.nextPageUrl
                    )
                    list.add(recData)
                }
                if (response.itemList[i].type == "videoSmallCard") {
                    val recData = RecData(
                        "",
                        response.itemList[i].data.cover.feed,
                        response.itemList[i].data.playUrl,
                        response.itemList[i].data.duration,
                        response.itemList[i].data.title,
                        "${response.itemList[i].data.author.name} / #${response.itemList[i].data.category}",
                        response.itemList[i].data.description,
                        response.itemList[i].data.id.toString(),
                        response.itemList[i].data.cover.blurred,
                        response.itemList[i].data.author.icon,
                        response.itemList[i].type,
                        response.nextPageUrl
                    )
                    list.add(recData)
                }
            }


        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        Result.success(list)
    }

    fun loadSearch(query: String) = fire(Dispatchers.IO) {
        val response = OpenEyeNetwork.loadSearch(query)
        val length = response.count
        val list = mutableListOf<RecData>()
        for (i in 0 until length) {
            if (response.itemList[i].type == "squareCardCollection") {
                val s = response.itemList[i].data
                val len = s.count
                for (j in 0 until len) {
                    try {
                        val recData = RecData(
                            response.itemList[i].data.header.title,
                            s.itemList[j].data.content.data.cover.feed,
                            s.itemList[j].data.content.data.playUrl,
                            s.itemList[j].data.content.data.duration,
                            s.itemList[j].data.content.data.title,
                            "${s.itemList[j].data.content.data.author.name} / #${s.itemList[j].data.content.data.category}",
                            s.itemList[j].data.content.data.description,
                            s.itemList[j].data.content.data.id.toString(),
                            s.itemList[j].data.content.data.cover.blurred,
                            s.itemList[j].data.content.data.author.icon,
                            "squareCardCollection",
                            response.nextPageUrl
                        )
                        list.add(recData)
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }

                }
            }
            if (response.itemList[i].type == "textCard" && response.itemList[i].data.type != "footer2" && length > 1) {
                val recData = RecData(
                    response.itemList[i].data.text,
                    "", "", 0, "", "", "", "", "", "", response.itemList[i].type,
                    response.nextPageUrl?:""
                )
                list.add(recData)
            }
            if (response.itemList[i].type == "banner2") {
                val recData = RecData(
                    "",
                    response.itemList[i].data.image,
                    "",
                    0,
                    response.itemList[i].data.header.title,
                    "广告",
                    response.itemList[i].data.header.description,
                    "",
                    "",
                    response.itemList[i].data.header.icon,
                    "squareCardCollection",
                    response.nextPageUrl
                )
                list.add(recData)
            }
            if (response.itemList[i].type == "followCard") {
                val recData = RecData(
                    "",
                    response.itemList[i].data.content.data.cover.feed,
                    response.itemList[i].data.content.data.playUrl,
                    response.itemList[i].data.content.data.duration,
                    response.itemList[i].data.content.data.title,
                    "${response.itemList[i].data.content.data.author.name} / #${response.itemList[i].data.content.data.category}",
                    response.itemList[i].data.content.data.description,
                    response.itemList[i].data.content.data.id.toString(),
                    response.itemList[i].data.content.data.cover.blurred,
                    response.itemList[i].data.content.data.author.icon,
                    response.itemList[i].type,
                    response.nextPageUrl
                )
                list.add(recData)
            }
            if (response.itemList[i].type == "videoSmallCard") {
                val recData = RecData(
                    "",
                    response.itemList[i].data.cover.feed,
                    response.itemList[i].data.playUrl,
                    response.itemList[i].data.duration,
                    response.itemList[i].data.title,
                    "${response.itemList[i].data.author.name} / #${response.itemList[i].data.category}",
                    response.itemList[i].data.description,
                    response.itemList[i].data.id.toString(),
                    response.itemList[i].data.cover.blurred,
                    response.itemList[i].data.author.icon,
                    response.itemList[i].type,
                    response.nextPageUrl
                )
                list.add(recData)
            }
            if (response.itemList[i].type == "briefCard") {
                val recData = RecData(
                    "",
                    "",
                    "",
                    0,
                    response.itemList[i].data.title,
                    "",
                    response.itemList[i].data.description ?: "",
                    response.itemList[i].data.id.toString(),
                    "",
                    response.itemList[i].data.icon,
                    response.itemList[i].type,
                    response.nextPageUrl
                )
                list.add(recData)
            }
        }
        Result.success(list)
    }

    fun loadCommunity() = fire(Dispatchers.IO) {
        val response = OpenEyeNetwork.loadCommunity()
        val length = response.count
        val list = mutableListOf<CommunityRecData>()

        try {
            for (i in 0 until length) {
                if (response.itemList[i].type == "communityColumnsCard") {
                    val communityData = CommunityRecData(
                        response.itemList[i].data.content.data.cover.feed,
                        response.itemList[i].data.content.data.url,
                        response.itemList[i].data.content.data.description,
                        response.itemList[i].data.content.data.owner.avatar,
                        response.itemList[i].data.content.data.owner.nickname,
                        response.itemList[i].data.content.data.consumption.collectionCount,
                        response.nextPageUrl
                    )
                    list.add(communityData)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


        Result.success(list)
    }

    fun loadCommunityFollow() = fire(Dispatchers.IO) {
        val response = OpenEyeNetwork.loadCommunityFollow()
        val length = response.count
        val list = mutableListOf<CommunityFollowData>()

        try {

            for (i in 0 until length) {
                val s = response.itemList[i].data.content.data
                if (response.itemList[i].type == "autoPlayFollowCard") {
                    val communityFollowData = CommunityFollowData(
                        s.cover.feed,
                        s.playUrl,
                        s.duration,
                        s.date,
                        s.title,
                        s.author.name,
                        s.description,
                        s.id.toString(),
                        s.cover.blurred,
                        s.author.icon,
                        s.consumption.collectionCount,
                        s.consumption.replyCount,
                        s.consumption.shareCount,
                        response.itemList[i].type,
                        response.nextPageUrl
                    )
                    list.add(communityFollowData)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


        Result.success(list)
    }

    fun loadMoreFollow(url: String) = fire(Dispatchers.IO) {
        val response = OpenEyeNetwork.loadMore(url)
        val length = response.count
        val list = mutableListOf<CommunityFollowData>()
        try {
            for (i in 0 until length) {
                val s = response.itemList[i].data.content.data
                if (response.itemList[i].type == "autoPlayFollowCard") {
                    val communityFollowData = CommunityFollowData(
                        s.cover.feed,
                        s.playUrl,
                        s.duration,
                        s.date,
                        s.title,
                        s.author.name ?: "",
                        s.description,
                        s.id.toString(),
                        s.cover.blurred,
                        s.author.icon,
                        s.consumption.collectionCount,
                        s.consumption.replyCount,
                        s.consumption.shareCount,
                        response.itemList[i].type,
                        response.nextPageUrl
                    )
                    list.add(communityFollowData)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


        Result.success(list)
    }

    fun loadMoreCommunityRec(url: String) = fire(Dispatchers.IO) {
        val response = OpenEyeNetwork.loadMoreCommunityRec(url)
        val length = response.count
        val list = mutableListOf<CommunityRecData>()
        for (i in 0 until length) {
            if (response.itemList[i].type == "communityColumnsCard") {
                val communityData = CommunityRecData(
                    response.itemList[i].data.content.data.cover.feed,
                    response.itemList[i].data.content.data.url,
                    response.itemList[i].data.content.data.description,
                    response.itemList[i].data.content.data.owner.avatar,
                    response.itemList[i].data.content.data.owner.nickname,
                    response.itemList[i].data.content.data.consumption.collectionCount,
                    response.nextPageUrl
                )
                list.add(communityData)
            }
        }

        Result.success(list)
    }

    fun loadDaily() = fire(Dispatchers.IO) {
        val response = OpenEyeNetwork.loadDaily()
        val length = response.count
        val list = mutableListOf<RecData>()
        try {
            for (i in 0 until length) {
                if (response.itemList[i].type == "textCard" && response.itemList[i].data.type != "footer2" && response.itemList[i].data.type != "header7" && length > 1) {
                    val recData = RecData(
                        response.itemList[i].data.text,
                        "", "", 0, "", "", "", "", "", "", response.itemList[i].type,
                        response.nextPageUrl
                    )
                    list.add(recData)
                }

                if (response.itemList[i].type == "followCard") {
                    val recData = RecData(
                        "",
                        response.itemList[i].data.content.data.cover.feed,
                        response.itemList[i].data.content.data.playUrl,
                        response.itemList[i].data.content.data.duration,
                        response.itemList[i].data.content.data.title,
                        "${response.itemList[i].data.content.data.author.name ?: ""} / #${response.itemList[i].data.content.data.category}",
                        response.itemList[i].data.content.data.description,
                        response.itemList[i].data.content.data.id.toString(),
                        response.itemList[i].data.content.data.cover.blurred,
                        response.itemList[i].data.content.data.author.icon,
                        "squareCardCollection",
                        response.nextPageUrl
                    )
                    list.add(recData)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


        Result.success(list)
    }

    fun loadMoreDaily(url: String) = fire(Dispatchers.IO) {
        val response = OpenEyeNetwork.loadMore(url)
        val length = response.count
        val list = mutableListOf<RecData>()
        try {
            for (i in 0 until length) {
                if (response.itemList[i].type == "textCard" && response.itemList[i].data.type != "footer2" && response.itemList[i].data.type != "header7" && length > 1) {
                    val recData = RecData(
                        response.itemList[i].data.text,
                        "", "", 0, "", "", "", "", "", "", response.itemList[i].type,
                        response.nextPageUrl
                    )
                    list.add(recData)
                }

                if (response.itemList[i].type == "followCard") {
                    val recData = RecData(
                        "",
                        response.itemList[i].data.content.data.cover.feed,
                        response.itemList[i].data.content.data.playUrl,
                        response.itemList[i].data.content.data.duration,
                        response.itemList[i].data.content.data.title,
                        "${response.itemList[i].data.content.data.author.name ?: ""} / #${response.itemList[i].data.content.data.category}",
                        response.itemList[i].data.content.data.description,
                        response.itemList[i].data.content.data.id.toString(),
                        response.itemList[i].data.content.data.cover.blurred,
                        response.itemList[i].data.content.data.author.icon,
                        "squareCardCollection",
                        response.nextPageUrl
                    )
                    list.add(recData)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


        Result.success(list)
    }

    fun loadMessage() = fire(Dispatchers.IO) {
        val response = OpenEyeNetwork.loadMessage()
        val length = response.messageList.size
        val list = mutableListOf<MessageData>()
        for (i in 0 until length) {
            val messageData = MessageData(
                response.messageList[i].title,
                response.messageList[i].content,
                response.messageList[i].date,
                response.nextPageUrl
            )
            list.add(messageData)
        }

        Result.success(list)

    }

    fun loadMoreMessage(url: String) = fire(Dispatchers.IO) {
        val response = OpenEyeNetwork.loadMoreMessage(url)
        val length = response.messageList.size
        val list = mutableListOf<MessageData>()
        try {
            for (i in 0 until length) {
                val messageData = MessageData(
                    response.messageList[i].title,
                    response.messageList[i].content,
                    response.messageList[i].date,
                    response.nextPageUrl
                )
                list.add(messageData)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        Result.success(list)
    }

    fun loadMoreTagRec(url: String) = fire(Dispatchers.IO) {
        val response = OpenEyeNetwork.loadMoreTagRec(url)
        val length = response.count
        val list = mutableListOf<TagRecData>()
        for (i in 0 until length) {
            if (response.itemList[i].type == "textCard" && response.itemList[i].data.type != "footer2" && length > 1) {
                val tagRecData = TagRecData(
                    response.itemList[i].data.text,
                    "", "", 0, "", "", "", "", "", "", response.itemList[i].type,
                    response.nextPageUrl, 0, 0, 0, 0
                )
                list.add(tagRecData)
            }
            if (response.itemList[i].type == "videoSmallCard") {
                val tagRecData = TagRecData(
                    "",
                    response.itemList[i].data.cover.feed,
                    response.itemList[i].data.playUrl,
                    response.itemList[i].data.duration,
                    response.itemList[i].data.title,
                    "${response.itemList[i].data.author.name} / #${response.itemList[i].data.category}",
                    response.itemList[i].data.description,
                    response.itemList[i].data.id.toString(),
                    response.itemList[i].data.cover.blurred,
                    response.itemList[i].data.author.icon,
                    response.itemList[i].type,
                    response.nextPageUrl, 0, 0, 0, 0
                )
                list.add(tagRecData)
            }

            if (response.itemList[i].type == "followCard") {
                val tagRecData = TagRecData(
                    "",
                    response.itemList[i].data.content.data.cover.feed,
                    response.itemList[i].data.content.data.playUrl,
                    response.itemList[i].data.content.data.duration,
                    response.itemList[i].data.content.data.title,
                    "${response.itemList[i].data.content.data.author.name} / #${response.itemList[i].data.content.data.category}",
                    response.itemList[i].data.content.data.description,
                    response.itemList[i].data.content.data.id.toString(),
                    response.itemList[i].data.content.data.cover.blurred,
                    response.itemList[i].data.content.data.author.icon,
                    response.itemList[i].type,
                    response.nextPageUrl, 0, 0, 0, 0
                )
                list.add(tagRecData)
            }

            if (response.itemList[i].type == "autoPlayFollowCard") {
                val tagRecData = TagRecData(
                    "",
                    response.itemList[i].data.content.data.cover.feed,
                    response.itemList[i].data.content.data.playUrl,
                    response.itemList[i].data.content.data.duration,
                    response.itemList[i].data.content.data.title,
                    response.itemList[i].data.content.data.author.name,
                    response.itemList[i].data.content.data.description,
                    response.itemList[i].data.content.data.id.toString(),
                    response.itemList[i].data.content.data.cover.blurred,
                    response.itemList[i].data.content.data.author.icon,
                    response.itemList[i].type, response.nextPageUrl,
                    response.itemList[i].data.content.data.consumption.collectionCount,
                    response.itemList[i].data.content.data.consumption.replyCount,
                    response.itemList[i].data.content.data.consumption.shareCount,
                    response.itemList[i].data.content.data.date
                )
                list.add(tagRecData)
            }
        }

        Result.success(list)
    }

    fun loadTagRec(id: String) = fire(Dispatchers.IO) {
        val response = OpenEyeNetwork.loadTagRec(id)
        val length = response.count
        val list = mutableListOf<TagRecData>()
        for (i in 0 until length) {
            if (response.itemList[i].type == "textCard" && response.itemList[i].data.type != "footer2" && length > 1) {
                val tagRecData = TagRecData(
                    response.itemList[i].data.text,
                    "", "", 0, "", "", "", "", "", "", response.itemList[i].type,
                    response.nextPageUrl, 0, 0, 0, 0
                )
                list.add(tagRecData)
            }
            if (response.itemList[i].type == "videoSmallCard") {
                val tagRecData = TagRecData(
                    "",
                    response.itemList[i].data.cover.feed,
                    response.itemList[i].data.playUrl,
                    response.itemList[i].data.duration,
                    response.itemList[i].data.title,
                    "${response.itemList[i].data.author.name} / #${response.itemList[i].data.category}",
                    response.itemList[i].data.description,
                    response.itemList[i].data.id.toString(),
                    response.itemList[i].data.cover.blurred,
                    response.itemList[i].data.author.icon,
                    response.itemList[i].type,
                    response.nextPageUrl, 0, 0, 0, 0
                )
                list.add(tagRecData)
            }

            if (response.itemList[i].type == "followCard") {
                val tagRecData = TagRecData(
                    "",
                    response.itemList[i].data.content.data.cover.feed,
                    response.itemList[i].data.content.data.playUrl,
                    response.itemList[i].data.content.data.duration,
                    response.itemList[i].data.content.data.title,
                    "${response.itemList[i].data.content.data.author.name} / #${response.itemList[i].data.content.data.category}",
                    response.itemList[i].data.content.data.description,
                    response.itemList[i].data.content.data.id.toString(),
                    response.itemList[i].data.content.data.cover.blurred,
                    response.itemList[i].data.content.data.author.icon,
                    response.itemList[i].type,
                    response.nextPageUrl, 0, 0, 0, 0
                )
                list.add(tagRecData)
            }

            if (response.itemList[i].type == "autoPlayFollowCard") {
                val tagRecData = TagRecData(
                    "",
                    response.itemList[i].data.content.data.cover.feed,
                    response.itemList[i].data.content.data.playUrl,
                    response.itemList[i].data.content.data.duration,
                    response.itemList[i].data.content.data.title,
                    response.itemList[i].data.content.data.author.name,
                    response.itemList[i].data.content.data.description,
                    response.itemList[i].data.content.data.id.toString(),
                    response.itemList[i].data.content.data.cover.blurred,
                    response.itemList[i].data.content.data.author.icon,
                    response.itemList[i].type, response.nextPageUrl,
                    response.itemList[i].data.content.data.consumption.collectionCount,
                    response.itemList[i].data.content.data.consumption.replyCount,
                    response.itemList[i].data.content.data.consumption.shareCount,
                    response.itemList[i].data.content.data.date
                )
                list.add(tagRecData)
            }
        }

        Result.success(list)
    }

    fun loadTagInfo(id: String) = fire(Dispatchers.IO) {
        val response = OpenEyeNetwork.loadTagInfo(id)
        val tagInfoData = TagInfoData(
            response.tagInfo.name,
            response.tagInfo.description ?: "",
            response.tagInfo.headerImage
        )
        Result.success(tagInfoData)
    }

    fun loadAllTag() = fire(Dispatchers.IO) {
        val response = OpenEyeNetwork.loadAllTag()
        val length = response.count
        val list = mutableListOf<AllTagData>()
        try {
            for (i in 0 until length) {
                val allTagData = AllTagData(
                    response.itemList[i].data.title,
                    response.itemList[i].data.image,
                    response.itemList[i].data.id.toString()
                )
                list.add(allTagData)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


        Result.success(list)
    }

    fun loadCalender(date: String) = fire(Dispatchers.IO) {
//        val response = OpenEyeNetwork.loadCalender(date)
        // 为修复接口目前时间返回结果变化采取固定时间方式
        val response = OpenEyeNetwork.loadCalender("2020-08-31")
        val length = response.count
        val listCommunityRecData = mutableListOf<CommunityRecData>()
        for (i in 3 until length) {
            if (response.itemList[i].type == "communityColumnsCard") {
                val communityData = CommunityRecData(
                    response.itemList[i].data.content.data.cover.feed,
                    response.itemList[i].data.content.data.url,
                    response.itemList[i].data.content.data.description,
                    response.itemList[i].data.content.data.owner.avatar,
                    response.itemList[i].data.content.data.owner.nickname,
                    response.itemList[i].data.content.data.consumption.collectionCount,
                    response.nextPageUrl
                )
                listCommunityRecData.add(communityData)
            }
        }

        val calenderData = CalenderData(
            response.itemList[0].data.weeklyDestination,
            response.itemList[0].data.dailyDestination,
            response.itemList[0].data.recReason,
            response.itemList[1].data.imageUrl,
            response.itemList[1].data.weeklyDestination,
            response.itemList[1].data.recReason,
            listCommunityRecData
        )
        Result.success(calenderData)
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}