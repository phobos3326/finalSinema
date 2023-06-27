package com.example.skillsinema.ui.main.showAll


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skillsinema.R
import com.example.skillsinema.adapter.Film

import com.example.skillsinema.databinding.FragmentShowAllBinding
import com.example.skillsinema.entity.Model
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class ShowAllFragment : Fragment() {


    private var _binding: FragmentShowAllBinding? = null
    private val binding get() = _binding!!



    val bundle =Bundle()

    private val adapterPagedFilm =AdapterPagedFilm {onItemClick(it)}

    companion object {
        //fun newInstance() = ShowAllFragment()
    }

    private val viewModel: ShowAllViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentShowAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.pagedFilms.onEach {
            binding.SHOWALLRecyclerView.adapter = adapterPagedFilm
            //dapterBestFilms.loading = false

            adapterPagedFilm.submitData(it)
            //adapterBestFilms.loading =true

        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }


    private fun onItemClick(item: Film) {
        bundle.putString("Arg", binding.textView.text.toString())
        findNavController().navigate(R.id.action_showAllFragment_to_itemInfoFragment, bundle)

    }

}