package com.lint0t.openeye.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.lint0t.openeye.bean.RecData
import com.lint0t.openeye.repository.Repository

class RecFragmentViewModel : ViewModel() {
    var listData = mutableListOf<RecData>()
    private val recLiveData = MutableLiveData<Int>()
    val recPathData = Transformations.switchMap(recLiveData) { Repository.loadRec() }
    fun loadRec() {
        recLiveData.value = 0
    }
    private val moreLiveData = MutableLiveData<String>()
    val morePathData = Transformations.switchMap(moreLiveData) {url-> Repository.loadMore(url) }
    fun loadMore(url:String?) {
        moreLiveData.value = url
    }
}