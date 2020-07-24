package com.lint0t.openeye.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.lint0t.openeye.bean.AllTagData
import com.lint0t.openeye.repository.Repository

class AllTagViewModel:ViewModel() {
    val listData = mutableListOf<AllTagData>()
    private val allTagLiveData = MutableLiveData<Int>()
    val allTagPathData =
        Transformations.switchMap(allTagLiveData) { Repository.loadAllTag() }

    fun loadAllTag() {
        allTagLiveData.value = 0
    }
}