package com.lint0t.openeye.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.lint0t.openeye.bean.CommunityFollowData
import com.lint0t.openeye.bean.CommunityRecData
import com.lint0t.openeye.bean.MessageData
import com.lint0t.openeye.repository.Repository

class MessageFragmentViewModel : ViewModel() {
    val listData = mutableListOf<MessageData>()
    private val messageLiveData = MutableLiveData<Int>()
    val messagePathData =
        Transformations.switchMap(messageLiveData) { Repository.loadMessage() }

    fun loadMessage() {
        messageLiveData.value = 0
    }

    private val moreLiveData = MutableLiveData<String>()
    val morePathData =
        Transformations.switchMap(moreLiveData) { url -> Repository.loadMoreMessage(url) }

    fun loadMore(url: String) {
        moreLiveData.value = url
    }
}