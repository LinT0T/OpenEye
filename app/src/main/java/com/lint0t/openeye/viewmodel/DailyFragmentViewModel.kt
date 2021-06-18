package com.lint0t.openeye.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.lint0t.openeye.bean.RecData
import com.lint0t.openeye.repository.Repository

class DailyFragmentViewModel : ViewModel() {
    var listData = mutableListOf<RecData>()
    private val dailyLiveData = MutableLiveData<Int>()
    val dailyPathData = Transformations.switchMap(dailyLiveData) { Repository.loadDaily() }
    fun loadDaily() {
        dailyLiveData.value = 0
    }
    private val moreLiveData = MutableLiveData<String>()
    val morePathData = Transformations.switchMap(moreLiveData) {url-> Repository.loadMoreDaily(url) }
    fun loadMore(url:String?) {
        moreLiveData.value = url
    }
}