package com.example.skillsinema.presentation.ui.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skillsinema.R
import com.example.skillsinema.dao.CollectionsEntity
import com.example.skillsinema.dao.InterestedItemEntity
import com.example.skillsinema.databinding.FragmentThirdBinding
import com.example.skillsinema.presentation.ui.adapters.AdapterInterestedItem
import com.example.skillsinema.presentation.ui.adapters.CollectionAdapter
import com.example.skillsinema.presentation.ui.model.RVDataType
import com.example.skillsinema.presentation.ui.model.TypeItem
import com.example.skillsinema.presentation.ui.profile.menu.AlertDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ThirdFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    val bundle = Bundle()
    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!

    val viewModel: ThirdFragmentViewModel by viewModels()

    val adapterInterestedItem = AdapterInterestedItem(
        onClick = { item, typeItem -> omClickInterestedItem(item, typeItem) }
    )

    val adapter = CollectionAdapter(
        onClick = { item, rvType -> onClickShowCollection(item, rvType) },
        onDelete = { item -> onDelete(item) }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeCollections()
    }

    private fun setupRecyclerView() {
        binding.recyclerView2.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterInterestedItem
        }

        binding.collectionRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapter
        }
    }

    private fun observeCollections() {
        viewModel.interestedCollection.onEach {
            adapterInterestedItem.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.collection.onEach {
            adapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun omClickInterestedItem(item: InterestedItemEntity, typeItem: TypeItem) {
        when (typeItem) {
            TypeItem.FILM -> {
                bundle.putInt("Arg", item.idItem)
                findNavController().navigate(R.id.action_fragment_third_to_itemInfoFragment, bundle)
            }
            TypeItem.PERSON -> {
                bundle.putInt("ArgActor", item.idItem)
                findNavController().navigate(R.id.action_fragment_third_to_actorInfoFragment, bundle)
            }
            else -> {}
        }
    }

    private fun onClickShowCollection(item: CollectionsEntity, rvType: RVDataType) {
        bundle.putIntegerArrayList("collection", item.collection?.toCollection(ArrayList()))
        bundle.putSerializable("Arg3", rvType)
        findNavController().navigate(R.id.action_fragment_third_to_showAllFragment, bundle)
    }

    private fun onDelete(item: CollectionsEntity) {
        viewModel.deleteCollection(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
