package com.example.skillsinema.ui.main.profile.menu

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
import com.example.skillsinema.dao.CollectionsEntity
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

    //val viewModel: ThirdFragmentViewModel by viewModels()
    val viewModel: CollectionDialogViewModel by viewModels()

    // lateinit var binding: BottomSheetDialogLayoutBinding
    val layoutManager = LinearLayoutManager(context)


    class MyDialogFragment : DialogFragment() {}


    val adapter = AddCollectionAdapterTWO(
        onChecked = { item -> onItemChecked(item) },
        onDelete = {item->onItemDelete(item)}
    )

    val bundle = Bundle()


    override fun getTheme() = R.style.AppBottomSheetDialogTheme
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return inflater.inflate(R.layout.bottom_sheet_dialog_layout, container, false)
        //_binding = BottomSheetDialogLayoutBinding.bind(inflater.inflate(R.layout.bottom_sheet_dialog_layout, container))
        _binding = BottomSheetDialogLayoutBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val id = arguments?.getInt("data")



        Log.d(TAG, "onViewCreated   $id")
        /*viewModel.collection.onEach {
            binding.recyclerView.adapter = adapter
            adapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)*/

        /* dialog.let {
             val bottomSheetDialog = BottomSheetDialog(requireContext())
             val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
             bottomSheet?.layoutParams?.height = 600 // Set the desired height in pixels

             bottomSheetDialog.setContentView(binding.root)
             bottomSheetDialog.show()
         }
 */



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
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            //binding.recyclerView.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
            adapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }


    fun onItemChecked(item: CollectionsEntity) {


        item.id.let {
            viewModel.update(item)
        }


    }

    fun onItemDelete(item: CollectionsEntity) {

        item.id.let {
            viewModel.deleteFilmFromDB(item)
        }

    }


    override fun onStart() {
        super.onStart()

        Log.d(TAG, "onStart")

        // Плотность понадобится нам в дальнейшем
        val density = requireContext().resources.displayMetrics.density

        dialog?.let {
            // Находим сам bottomSheet и достаём из него Behaviour
            val bottomSheet =
                it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)

            // Выставляем высоту для состояния collapsed и выставляем состояние collapsed
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