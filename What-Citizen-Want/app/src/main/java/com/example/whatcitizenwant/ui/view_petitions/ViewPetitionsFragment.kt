package com.example.whatcitizenwant.ui.view_petitions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.whatcitizenwant.R

class ViewPetitionsFragment : Fragment() {

    private lateinit var viewPetitionsViewModel: ViewPetitionsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewPetitionsViewModel =
                ViewModelProviders.of(this).get(ViewPetitionsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_view_petitions, container, false)
        val textView: TextView = root.findViewById(R.id.text_view_petitions)
        viewPetitionsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}