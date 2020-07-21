package com.lint0t.openeye.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.lint0t.openeye.bean.RecData
import com.lint0t.openeye.bean.TagRecData
import com.lint0t.openeye.repository.Repository

class TagViewModel : ViewModel() {
    var listData = mutableListOf<TagRecData>()
    private val tagLiveData = MutableLiveData<String>()
    val tagPathData = Transformations.switchMap(tagLiveData) { id ->
        Repository.loadTagInfo(id)
    }
    val tagRecPathData = Transformations.switchMap(tagLiveData) { id ->
        Repository.loadTagRec(id)
    }

    fun loadTagInfo(id: String) {
        tagLiveData.value = id
    }


    private val moreLiveData = MutableLiveData<String>()
    val morePathData =
        Transformations.switchMap(moreLiveData) { url -> Repository.loadMoreTagRec(url) }

    fun loadMore(url: String) {
        moreLiveData.value = url
    }
}