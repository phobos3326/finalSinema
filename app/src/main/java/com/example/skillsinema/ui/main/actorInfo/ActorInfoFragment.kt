package com.example.skillsinema.ui.main.actorInfo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.skillsinema.R

class ActorInfoFragment : Fragment() {

    companion object {
        fun newInstance() = ActorInfoFragment()
    }

    private lateinit var viewModel: ActorInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_actor_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActorInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }



}