package com.example.skillsinema.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.skillsinema.R
import com.example.skillsinema.adapter.ViewPagerAdapter
import com.example.skillsinema.databinding.FragmentMainBinding

class MainFragment : Fragment(), ViewPagerAdapter.ConditionViewPager {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    private val data = mutableListOf<String>()



    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

//        binding.viewPager.adapter =ViewPagerAdapter(data,this)
        addToList()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container,false)
        binding.viewPager.adapter =ViewPagerAdapter(data,this)



        return (binding.root)

    }

    private fun addToList() {
        for (item in 1..20) {
            data.add("item $item")
        }
    }

    override fun condition(position: Int, fullSize: Int) {

        if (position == fullSize) {

        }
        Toast.makeText(requireContext(), "$position / $fullSize", Toast.LENGTH_SHORT).show()

    }


}