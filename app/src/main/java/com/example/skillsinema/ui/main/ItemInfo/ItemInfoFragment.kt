package com.example.skillsinema.ui.main.ItemInfo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.core.graphics.green
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
/*import com.example.skillsinema.MyComponentManager
import com.example.skillsinema.MyEntryPoint*/
import com.example.skillsinema.R
import com.example.skillsinema.entity.ModelFilmDetails
import com.example.skillsinema.databinding.FragmentItemInfoBinding
import com.example.skillsinema.entity.Film
import com.example.skillsinema.entity.ModelActorInfo
import com.example.skillsinema.entity.ModelStaff
import com.example.skillsinema.ui.main.home.AdapterBestFilm
import dagger.hilt.EntryPoints
//import com.example.skillsinema.entity.ModelFilmDetails
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

@AndroidEntryPoint
class ItemInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentItemInfoBinding? = null
    private val binding get() = _binding!!

    val viewModel: ItemInfoViewModel by viewModels()

    val adapterActor = StaffAdapter {
        onItemActorClick(it)
    }
    val adapterNoActor = StaffAdapter {
        onItemActorClick(it)
    }
    val galerieAdapter = GalerieAdapter()
    val similarFilmAdapter = AdapterBestFilm {
        onItemDetailClick(it)
    }

    val bundle = Bundle()

    //@Inject
    //lateinit var myComponentManager: MyComponentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // myComponentManager.create()
        /* val myComponent =myComponentManager.get()
         val dataRepository=EntryPoints.get(myComponent,MyEntryPoint::class.java).getDataRepository()
         */


        arguments?.let {


        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentItemInfoBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt("Arg")
        Log.d("FRAGMENT ITEM", id.toString())

        binding.seriesAll.setOnClickListener {
            id?.let { it1 -> onShowAllSeaseonsClick(it1) }
            findNavController().navigate(R.id.action_itemInfoFragment_to_itemserialInfoFragment, bundle)
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        StateItemFilmInfo.FilmState -> {
                            binding.series.isVisible = false
                            binding.seriesAll.isVisible = false
                            binding.seriesDetails.isVisible = false

                        }

                        StateItemFilmInfo.SerialState -> {
                            binding.series.isVisible = true
                            binding.seriesAll.isVisible = true
                            binding.seriesDetails.isVisible = true
                        }
                    }
                }
            }
        }


        lifecycleScope.launch {
            viewModel.setValue(id!!)
            viewModel.film.observe(viewLifecycleOwner, Observer<ModelFilmDetails> {
                binding.filmTextView.text = "${it.ratingKinopoisk ?: ""} ${it.nameRu}"
                binding.YearGenreTextView.text =
                    "${it.year.toString()}, ${it.genres?.joinToString(", ") { it.genre.toString() }}"
                binding.CountryDuration.text = buildString {
                    append(it.countries?.take(4)?.joinToString(", ") { it.country.toString() })
                    if (it.filmLength == null) {
                        append("")
                    } else {
                        append(
                            it.filmLength.minutes.toComponents { hours, minutes, _, _ -> ", $hours ч:$minutes мин" }
                        )
                    }

                }
                binding.shortDescriptionTextView.text = it.shortDescription
                binding.DescriptionTextView.text = it.description

                Glide.with(this@ItemInfoFragment)
                    .load(it.posterUrlPreview)
                    .into(binding.filmPreviewImageView)

            })
        }

        viewModel.staff.onEach {
            binding.actorsRecycler.adapter = adapterActor
            adapterActor.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        viewModel.noActorStaff.onEach {
            binding.staffRecycler.adapter = adapterNoActor
            adapterNoActor.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        viewModel.pagedGalerie.onEach {
            binding.galerieRecycler.adapter = galerieAdapter
            galerieAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        viewModel.similar.onEach {
            binding.relatedMoviesRecycler.adapter = similarFilmAdapter
            similarFilmAdapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.galerieFilmAll.setOnClickListener {
            findNavController().navigate(R.id.action_itemInfoFragment_to_galerieFragment)
        }

    }


    private fun onItemDetailClick(item: Film) {
        if (item.kinopoiskId == null) {
            item.filmId?.let { bundle.putInt("Arg", it) }
        } else {
            item.kinopoiskId.let { bundle.putInt("Arg", it) }
        }
        findNavController().navigate(R.id.action_itemInfoFragment_self, bundle)
    }

    private fun onItemActorClick(item: ModelStaff.ModelStaffItem) {

        item.staffId?.let { bundle.putInt("ArgActor", it) }

        findNavController().navigate(R.id.action_itemInfoFragment_to_actorInfoFragment, bundle)
    }


    private fun onShowAllSeaseonsClick(id:Int){
        viewModel.setSeriesValue(id)
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ItemInfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ItemInfoFragment().apply {
                arguments = Bundle().apply {
                    //putString(com.example.skillsinema.ARG_PARAM1, param1)
                    //putString(com.example.skillsinema.ARG_PARAM2, param2)
                }
            }
    }
}