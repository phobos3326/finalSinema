package com.example.skillsinema.ui.main.profile.menu

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.skillsinema.R
import com.example.skillsinema.dao.CollectionsEntity
import com.example.skillsinema.ui.main.profile.CollectionAdapter
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class AlertDialogFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    val viewModel: CollectionDialogViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val input = EditText(requireContext())
            val builder = AlertDialog.Builder(it)

            builder.setTitle("Важное сообщение!")
                .setView(input)
                .setMessage("Покормите кота!")

                .setPositiveButton("ОК, иду на кухню") { dialog, id ->
                    val enteredText = input.text.toString()
                    viewModel.insertIdtoDB(enteredText)

                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}