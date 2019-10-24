package com.example.healthylifestyleapp.model

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class PageViewModel : ViewModel() {

    private val mIndex = MutableLiveData<Int>()
    val text = Transformations.map(mIndex) { input -> "Hello world from section: " + input!! }

    fun setIndex(index: Int) {
        mIndex.value = index
    }
}