package com.lint0t.openeye.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.lint0t.openeye.bean.SpecialData
import com.lint0t.openeye.repository.Repository

class DiscoveryFragmentViewModel : ViewModel() {

    val listData = mutableListOf<SpecialData>()
    private val bannerLiveData = MutableLiveData<Int>()
    val bannerPathData = Transformations.switchMap(bannerLiveData) { Repository.loadBanner() }
    fun loadBanner() {
        bannerLiveData.value = 0
    }
    val specialPathData = Transformations.switchMap(bannerLiveData) { Repository.loadSpecial() }

    val columnPathData = Transformations.switchMap(bannerLiveData) { Repository.loadColumn() }

    val topPathData = Transformations.switchMap(bannerLiveData) { Repository.loadTop() }
    val topicPathData = Transformations.switchMap(bannerLiveData) { Repository.loadTopic() }
}