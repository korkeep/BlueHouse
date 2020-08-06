package com.example.whatcitizenwant.ui.view_petitions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewPetitionsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is 'View Petitions' Fragment"
    }
    val text: LiveData<String> = _text
}