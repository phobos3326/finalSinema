package com.example.skillsinema.ui.main.ItemInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.skillsinema.R
import com.example.skillsinema.databinding.FragmentItemInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ItemInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentItemInfoBinding? = null
    private val binding get() = _binding!!

    val viewModel: ItemInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {



            // param1 = it.getString(com.example.skillsinema.ARG_PARAM1)
            //   param2 = it.getString(com.example.skillsinema.ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentItemInfoBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id= arguments?.getInt("Arg")

        lifecycleScope.launch {
            viewModel.id=id!!
            viewModel.film.observe(viewLifecycleOwner, Observer {

                //viewModel.loadFilm()
                binding.filmManeTextView.text = it.nameRu
            })

        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ItemInfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ItemInfoFragment().apply {
                arguments = Bundle().apply {
                    //putString(com.example.skillsinema.ARG_PARAM1, param1)
                    //putString(com.example.skillsinema.ARG_PARAM2, param2)
                }
            }
    }
}