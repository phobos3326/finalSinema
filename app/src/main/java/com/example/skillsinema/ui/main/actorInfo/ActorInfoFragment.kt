package com.example.skillsinema.ui.main.actorInfo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide

import com.example.skillsinema.R
import com.example.skillsinema.databinding.FragmentActorInfoBinding
import com.example.skillsinema.databinding.FragmentItemInfoBinding
import com.example.skillsinema.databinding.FragmentMainBinding
import com.example.skillsinema.entity.Film
import com.example.skillsinema.ui.main.home.AdapterBestFilm
import com.example.skillsinema.ui.main.home.AdapterFilteredFilms
import com.example.skillsinema.ui.main.home.RVDataType
import com.example.skillsinema.ui.main.home.TypeOfAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ActorInfoFragment @Inject constructor() : Fragment() {


    private var _binding: FragmentActorInfoBinding? = null
    private val binding get() = _binding!!

    /* companion object {
         fun newInstance() = ActorInfoFragment()
     }*/
    val adapter = AdapterFilteredFilms (
        onClick = {item-> onItemDetailClick(item)},

        onClickShowAll = {type, rvType->onClickShowAll(type, rvType) }
    )
    val bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // myComponentManager.create()
        /* val myComponent =myComponentManager.get()
         val dataRepository=EntryPoints.get(myComponent,MyEntryPoint::class.java).getDataRepository()
         */
        arguments?.let {


        }
    }

    private val viewModel: ActorInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActorInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actorId = arguments?.getInt("ArgActor")

        lifecycleScope.launch {
            viewModel.setValue(actorId!!)
            viewModel.actor.observe(viewLifecycleOwner) {
                binding.actorName.text = it.nameRu
                binding.actorProperty.text = it.profession
                Glide.with(this@ActorInfoFragment)
                    .load(it.posterUrl)
                    .into(binding.imageView)
            }
        }

        viewModel.pagedFilms.onEach {
            binding.bestActorsMovies.adapter = adapter
            adapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)



    }

    private fun onClickShowAll(type: TypeOfAdapter, rvType: RVDataType) {


        when(type){
            TypeOfAdapter.WITHOUTPAGING -> {
                bundle.putSerializable("Arg2", TypeOfAdapter.WITHOUTPAGING)
            }
            TypeOfAdapter.WITHPAGING->{
                bundle.putSerializable("Arg2", TypeOfAdapter.WITHPAGING)
            }
        }

        when(rvType){
            RVDataType.TOP250->{
                bundle.putSerializable("Arg3", RVDataType.TOP250)
            }
            RVDataType.COUNTRYWITHGENRE->{
                bundle.putSerializable("Arg3", RVDataType.COUNTRYWITHGENRE)
            }
            RVDataType.PREMIERES->{
                bundle.putSerializable("Arg3", RVDataType.PREMIERES)
            }
            RVDataType.SERIALS->{
                bundle.putSerializable("Arg3", RVDataType.SERIALS)
            }
        }

        findNavController().navigate(R.id.action_home_fragment_to_showAllFragment, bundle)
    }

    private fun onItemDetailClick(item: Film) {
        if (item.kinopoiskId == null) {
            item.filmId?.let { bundle.putInt("Arg", it) }
        } else {
            item.kinopoiskId.let { bundle.putInt("Arg", it) }
        }
        findNavController().navigate(R.id.action_actorInfoFragment_to_itemInfoFragment, bundle)
    }
    /* override fun onActivityCreated(savedInstanceState: Bundle?) {
         super.onActivityCreated(savedInstanceState)
         viewModel = ViewModelProvider(this).get(ActorInfoViewModel::class.java)
         // TODO: Use the ViewModel
     }*/


}