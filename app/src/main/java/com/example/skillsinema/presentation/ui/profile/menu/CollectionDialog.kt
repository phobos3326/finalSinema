package com.example.skillsinema.presentation.ui.profile.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skillsinema.R
import com.example.skillsinema.databinding.BottomSheetDialogLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val COLLAPSED_HEIGHT = 300

@AndroidEntryPoint
class CollectionDialog : BottomSheetDialogFragment() {

    private val TAG = "ContentFragment"

    private var _binding: BottomSheetDialogLayoutBinding? = null
    private val binding get() = _binding!!

    val viewModel: CollectionDialogViewModel by viewModels()

    val layoutManager = LinearLayoutManager(context)

    class MyDialogFragment : DialogFragment() {}

    val adapter = AddCollectionAdapterTWO(
        onChecked = { item -> onItemChecked(item) }
    )

    val bundle = Bundle()

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetDialogLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt("data")

        Log.d(TAG, "onViewCreated   $id")

        extracted()

        binding.createCollectionTextView.setOnClickListener {
            val myDialogFragment = AlertDialogFragment()
            val manager = requireActivity().supportFragmentManager
            val transaction: FragmentTransaction = manager.beginTransaction()
            myDialogFragment.show(transaction, "dialog")
            extracted()
        }
    }

    private fun extracted() {
        viewModel.collectionUi.onEach {
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    fun onItemChecked(item: CollectionsUiModel) {
        item.id.let {
            viewModel.update(item)
        }
    }

    fun onItemDelete(item: com.example.skillsinema.dao.CollectionsEntity) {
        viewModel.deleteFilmFromDB(item)
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "onStart")

        val density = requireContext().resources.displayMetrics.density

        dialog?.let {
            val bottomSheet =
                it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)

            behavior.peekHeight = (COLLAPSED_HEIGHT * density).toInt()
            behavior.maxHeight
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        extracted()
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
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach")
    }
}
