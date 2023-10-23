package com.example.skillsinema.ui.main.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.skillsinema.R
import com.example.skillsinema.databinding.FragmentFilmographyBinding
import com.example.skillsinema.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilmographyFragment : Fragment() {


    private var _binding: FragmentFilmographyBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = FilmographyFragment()
    }

//    private val

    private val viewModel: FilmographyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filmography, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProvider(this).get(FilmographyViewModel::class.java)
        // TODO: Use the ViewModel
    }

}