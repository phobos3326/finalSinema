package com.example.skillsinema.ui.main.ItemInfo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ItemInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentItemInfoBinding? = null
    private val binding get() = _binding!!

    val viewModel: ItemInfoViewModel by viewModels()

    val adapterActor =StaffAdapter {
        onItemActorClick(it)
    }
    val adapterNoActor =StaffAdapter{
        onItemActorClick(it)
    }
    val galerieAdapter =GalerieAdapter()
    val similarFilmAdapter =AdapterBestFilm{
        onItemDetailClick(it)
    }

    val bundle=Bundle()

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


        lifecycleScope.launch {
            viewModel.setValue(id!!)
            viewModel.film.observe(viewLifecycleOwner, Observer<ModelFilmDetails> {


                binding.filmTextView.text = it.nameRu
                binding.shortDescriptionTextView.text = it.shortDescription
                Glide.with(this@ItemInfoFragment)
                    .load(it.posterUrl)
                    .into(binding.filmPreviewImageView)

            })

        }


        viewModel.staff.onEach {
            binding.actorsRecycler.adapter =adapterActor
            adapterActor.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        viewModel.noActorStaff.onEach {
            binding.staffRecycler .adapter =adapterNoActor
            adapterNoActor.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        viewModel.pagedGalerie.onEach {
            binding.galerieRecycler.adapter=galerieAdapter
            galerieAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        viewModel.similar.onEach {
            binding.relatedMoviesRecycler.adapter=similarFilmAdapter
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