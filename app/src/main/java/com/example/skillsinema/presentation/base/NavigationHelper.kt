package com.example.skillsinema.presentation.base

import android.os.Bundle
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import com.example.skillsinema.R

/**
 * Helper для навигации между экранами.
 * Централизует всю навигационную логику.
 */
object NavigationHelper {

    /**
     * Навигация к деталям фильма
     */
    fun navigateToFilmDetails(fragment: Fragment, filmId: Int) {
        val bundle = Bundle().apply {
            putInt("film_id", filmId)
        }
        safeNavigate(fragment, R.id.action_mainFragment_to_itemInfoFragment, bundle)
    }

    /**
     * Навигация к деталям актёра
     */
    fun navigateToActorDetails(fragment: Fragment, actorId: Int) {
        val bundle = Bundle().apply {
            putInt("actor_id", actorId)
        }
        safeNavigate(fragment, R.id.action_itemInfoFragment_to_actorInfoFragment, bundle)
    }

    /**
     * Навигация к экрану "Показать все"
     */
    fun navigateToShowAll(fragment: Fragment, category: String) {
        val bundle = Bundle().apply {
            putString("category", category)
        }
        safeNavigate(fragment, R.id.action_home_fragment_to_showAllFragment, bundle)
    }

    /**
     * Навигация к галерее
     */
    fun navigateToGallery(fragment: Fragment) {
        safeNavigate(fragment, R.id.action_itemInfoFragment_to_galerieFragment)
    }

    /**
     * Навигация к просмотру изображения
     */
    fun navigateToImageViewer(fragment: Fragment, imageUrl: String) {
        val bundle = Bundle().apply {
            putString("imageUrl", imageUrl)
        }
        safeNavigate(fragment, R.id.action_galerieFragment_to_showImageFragment, bundle)
    }

    /**
     * Навигация к фильмографии
     */
    fun navigateToFilmography(fragment: Fragment) {
        safeNavigate(fragment, R.id.action_actorInfoFragment_to_filmographyFragment)
    }

    /**
     * Навигация к деталям сериала (сезоны)
     */
    fun navigateToSeasons(fragment: Fragment) {
        safeNavigate(fragment, R.id.action_itemInfoFragment_to_itemserialInfoFragment)
    }

    /**
     * Безопасная навигация с обработкой исключений
     */
    fun safeNavigate(
        fragment: Fragment,
        actionId: Int,
        args: Bundle? = null,
        navOptions: NavOptions? = null
    ) {
        try {
            fragment.findNavController().navigate(actionId, args, navOptions, null)
        } catch (e: Exception) {
            // Навигация не удалась (например, фрагмент не прикреплён)
            e.printStackTrace()
        }
    }

    /**
     * Навигация через NavDirections (Safe Args)
     */
    fun navigate(fragment: Fragment, directions: NavDirections) {
        try {
            fragment.findNavController().navigate(directions)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Навигация назад
     */
    fun navigateBack(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

    /**
     * Навигация назад к определённому экрану
     */
    fun navigateBackTo(fragment: Fragment, destinationId: Int, inclusive: Boolean = false) {
        fragment.findNavController().popBackStack(destinationId, inclusive)
    }
}
