package com.example.skillsinema.presentation.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

/**
 * Базовый класс для всех Fragment.
 * Устраняет дублирование кода обработки binding, ошибок и навигации.
 */
abstract class BaseFragment<T : ViewBinding> : Fragment() {

    private var _binding: T? = null
    protected val binding: T get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeState()
    }

    /**
     * Возвращает layout binding через ViewBinding
     */
    protected abstract fun inflateBinding(): T

    /**
     * Инициализация UI (адаптеры, listeners)
     */
    protected abstract fun setupUI()

    /**
     * Наблюдение за состоянием ViewModel
     */
    protected abstract fun observeState()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Показать Snackbar с сообщением
     */
    protected fun showError(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_LONG)
                .setAction("OK") { }
                .show()
        }
    }

    /**
     * Показать Snackbar с ресурсом строки
     */
    protected fun showError(@StringRes messageRes: Int) {
        view?.let {
            Snackbar.make(it, messageRes, Snackbar.LENGTH_LONG)
                .setAction("OK") { }
                .show()
        }
    }

    /**
     * Показать Toast с сообщением
     */
    protected fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
