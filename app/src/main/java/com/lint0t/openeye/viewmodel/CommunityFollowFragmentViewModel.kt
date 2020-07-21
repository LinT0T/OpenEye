package com.lint0t.openeye.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.lint0t.openeye.bean.CommunityFollowData
import com.lint0t.openeye.bean.CommunityRecData
import com.lint0t.openeye.repository.Repository

class CommunityFollowFragmentViewModel:ViewModel() {
    val listData = mutableListOf<CommunityFollowData>()
    private val communityFollowLiveData = MutableLiveData<Int>()
    val communityFollowPathData =
        Transformations.switchMap(communityFollowLiveData) { Repository.loadCommunityFollow() }

    fun loadFollow() {
        communityFollowLiveData.value = 0
    }

    private val moreLiveData = MutableLiveData<String>()
    val morePathData = Transformations.switchMap(moreLiveData) { url -> Repository.loadMoreFollow(url) }
    fun loadMore(url: String) {
        moreLiveData.value = url
    }
}