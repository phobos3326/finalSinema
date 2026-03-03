package com.example.skillsinema.presentation.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Extension функция для безопасного сбора StateFlow в Fragment
 * Автоматически управляет жизненным циклом
 */
fun <T> StateFlow<T>.collectWithLifecycle(
    fragment: Fragment,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    action: (T) -> Unit
) {
    fragment.viewLifecycleOwner.lifecycleScope.launch {
        fragment.viewLifecycleOwner.repeatOnLifecycle(minActiveState) {
            collect { action(it) }
        }
    }
}
