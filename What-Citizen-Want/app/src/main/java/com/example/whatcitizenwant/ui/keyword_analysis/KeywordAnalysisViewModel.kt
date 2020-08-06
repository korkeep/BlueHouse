package com.example.whatcitizenwant.ui.keyword_analysis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KeywordAnalysisViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is 'Keyword Analysis' Fragment"
    }
    val text: LiveData<String> = _text
}