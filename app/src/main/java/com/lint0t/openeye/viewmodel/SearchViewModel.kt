package com.lint0t.openeye.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.lint0t.openeye.bean.RecData
import com.lint0t.openeye.repository.Repository


class SearchViewModel : ViewModel() {
    val listData = mutableListOf<RecData>()
    private val searchLiveData = MutableLiveData<String>()
    val searchPathData =
        Transformations.switchMap(searchLiveData) { query -> Repository.loadSearch(query) }

    fun loadSearch(query: String) {
        searchLiveData.value = query
    }

    private val moreLiveData = MutableLiveData<String>()
    val morePathData = Transformations.switchMap(moreLiveData) { url -> Repository.loadMore(url) }
    fun loadMore(url: String?) {
        moreLiveData.value = url
    }
}