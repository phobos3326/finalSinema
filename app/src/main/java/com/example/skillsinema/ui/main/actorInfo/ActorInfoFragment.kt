package com.example.skillsinema.ui.main.actorInfo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide

import com.example.skillsinema.R
import com.example.skillsinema.databinding.FragmentActorInfoBinding
import com.example.skillsinema.databinding.FragmentItemInfoBinding
import com.example.skillsinema.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActorInfoFragment : Fragment() {


    private var _binding: FragmentActorInfoBinding? = null
    private val binding get() = _binding!!

   /* companion object {
        fun newInstance() = ActorInfoFragment()
    }*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // myComponentManager.create()
        /* val myComponent =myComponentManager.get()
         val dataRepository=EntryPoints.get(myComponent,MyEntryPoint::class.java).getDataRepository()
         */
        arguments?.let {


        }
    }

    private  val viewModel: ActorInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentActorInfoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actorId = arguments?.getInt("ArgActor")

        lifecycleScope.launch {
            viewModel.setValue(actorId!!)
            viewModel.actor.observe(viewLifecycleOwner){
                binding.actorName.text =it.nameRu
                binding.actorProperty.text=it.profession
                Glide.with(this@ActorInfoFragment)
                    .load(it.posterUrl)
                    .into(binding.imageView)
            }
        }

    }

   /* override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActorInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }*/



}