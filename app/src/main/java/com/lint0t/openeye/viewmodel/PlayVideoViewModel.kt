package com.lint0t.openeye.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.lint0t.openeye.repository.Repository

class PlayVideoViewModel:ViewModel() {

    private val relatedLiveData = MutableLiveData<String>()
    val relatedPathData = Transformations.switchMap(relatedLiveData) { query->
        Repository.loadRelated(query) }

    val replyPathData = Transformations.switchMap(relatedLiveData) { query->
        Repository.loadReply(query) }
    fun loadRelated(query: String) {
        relatedLiveData.value = query
    }
}