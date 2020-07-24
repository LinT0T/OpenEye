package com.lint0t.openeye.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.lint0t.openeye.bean.CommunityRecData
import com.lint0t.openeye.repository.Repository

class CalenderViewModel : ViewModel() {
    val listData = mutableListOf<CommunityRecData>()
    private val calenderLiveData = MutableLiveData<String>()
    val calenderPathData =
        Transformations.switchMap(calenderLiveData) { date -> Repository.loadCalender(date) }

    fun loadCalender(date: String) {
        calenderLiveData.value = date
    }

    private val moreLiveData = MutableLiveData<String>()
    val morePathData =
        Transformations.switchMap(moreLiveData) { url -> Repository.loadMoreCommunityRec(url) }

    fun loadMore(url: String) {
        moreLiveData.value = url
    }
}