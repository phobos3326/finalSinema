package com.example.skillsinema.ui.main.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skillsinema.R
import com.example.skillsinema.databinding.BottomSheetDialogLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionDialog() : BottomSheetDialogFragment() {

    lateinit var binding: BottomSheetDialogLayoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return inflater.inflate(R.layout.bottom_sheet_dialog_layout, container, false)
        binding = BottomSheetDialogLayoutBinding.bind(inflater.inflate(R.layout.bottom_sheet_dialog_layout, container))
        return binding.root
    }
}