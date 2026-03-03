package com.example.skillsinema.presentation.ui.profile.menu

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.skillsinema.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlertDialogFragment : DialogFragment() {

    val viewModel: CollectionDialogViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val input = EditText(requireContext())
            val builder = AlertDialog.Builder(it)

            builder.setTitle("Придумайте название для вашей новой коллекции!")
                .setView(input)
                .setPositiveButton("Готово") { dialog, _ ->
                    val enteredText = input.text.toString()
                    viewModel.insertIdtoDB(enteredText)
                    dialog.cancel()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
