package com.lint0t.openeye.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.lint0t.openeye.repository.Repository

class WelcomeViewModel:ViewModel() {
    private val imageLiveData = MutableLiveData<Int>()
    val imagePathData = Transformations.switchMap(imageLiveData) { Repository.loadImage() }
    fun loadImage() {
        imageLiveData.value = 0
    }
}