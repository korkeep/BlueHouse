package com.example.whatcitizenwant.ui.keyword_analysis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.whatcitizenwant.R

class KeywordAnalysisFragment : Fragment() {

    private lateinit var keywordAnalysisViewModel: KeywordAnalysisViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        keywordAnalysisViewModel =
                ViewModelProviders.of(this).get(KeywordAnalysisViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_keyword_analysis, container, false)
        val textView: TextView = root.findViewById(R.id.text_keyword_analysis)
        keywordAnalysisViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}