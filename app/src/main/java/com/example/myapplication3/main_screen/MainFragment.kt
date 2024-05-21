package com.example.myapplication3.main_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myapplication3.entity_info.EntityInfoViewModel
import com.example.myapplication3.R
import com.example.myapplication3.databinding.FragmentFirstBinding
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()
    private val entityInfoViewModel: EntityInfoViewModel by activityViewModels()
    private lateinit var adapter: EntityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = EntityAdapter {
            entityInfoViewModel.applyArgs(it)
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        with(binding) {
            recyclerView.adapter = adapter

            fab.setOnClickListener {
                entityInfoViewModel.applyArgs(null)
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                mainViewModel.state.collect(::renderScreen)
            }
        }
    }

    private fun renderScreen(mainScreenState: MainScreenState) {
        adapter.entitiesList = mainScreenState.entities
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}