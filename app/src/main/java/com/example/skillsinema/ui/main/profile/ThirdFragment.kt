package com.example.skillsinema.ui.main.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.skillsinema.R
import com.example.skillsinema.dao.CollectionsEntity
import com.example.skillsinema.databinding.FragmentItemInfoBinding
import com.example.skillsinema.databinding.FragmentThirdBinding
import com.example.skillsinema.ui.main.profile.menu.AlertDialogFragment
import com.example.skillsinema.ui.main.profile.menu.CollectionDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class ThirdFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!

    val viewModel: ThirdFragmentViewModel by viewModels()

    val adapter = CollectionAdapter {
        onDelete(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createCollectionTextView.setOnClickListener {
            /*viewModel.wantToSee()

            fragmentManager?.let { CollectionDialog().show(it,"bottomSheetDialogFragment.tag") }*/


        }

        extracted()


        binding.createCollectionTextView.setOnClickListener {

            val myDialogFragment = AlertDialogFragment()
            val manager = requireActivity().supportFragmentManager
            //myDialogFragment.show(manager, "myDialog")


            //myDialogFragment.show(transaction, "dialog")
            val transaction: FragmentTransaction = manager.beginTransaction()
            myDialogFragment.show(transaction, "dialog")


            // viewModel.insertIdtoDB()
        }


    }

    private fun extracted() {
        viewModel.collection.onEach {
            binding.collectionRecycler.adapter = adapter
            adapter.submitList(it)


        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    fun onDelete(item: CollectionsEntity) {
        viewModel.deleteCollection(item)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d(TAG, "onViewStateRestored")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach")
    }

    private val TAG = "ThirdFragment"


}