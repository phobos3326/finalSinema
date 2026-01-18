package com.example.skillsinema.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.skillsinema.databinding.FragmentShowImageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowImageFragment : Fragment() {

    private var _binding: FragmentShowImageBinding? = null
    private val binding get() = _binding!!

    private val args: ShowImageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageUrl = args.imageUrl

        if (imageUrl.isEmpty()) {
            findNavController().navigateUp()
            return
        }

        // Загружаем полноэкранное изображение
        Glide.with(this)
            .load(imageUrl)
            .fitCenter()
            .into(binding.fullImageView)

        // Закрытие по кнопке
        binding.closeButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // Можно добавить закрытие по клику на изображение
        binding.fullImageView.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
