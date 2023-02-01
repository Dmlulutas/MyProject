package com.example.myapplication.ui.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.data.models.Character
import com.example.myapplication.databinding.CharacterDetailBinding
import com.example.myapplication.databinding.FragmentFirstBinding
import com.example.myapplication.ui.adapters.CharactersAdapter
import com.example.myapplication.ui.viewModels.CharactersViewModel
import com.example.myapplication.ui.viewModels.FirstViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstFragment : Fragment() {
    private lateinit var charactersAdapter: CharactersAdapter
    private lateinit var firstViewModel: FirstViewModel
    private lateinit var charsViewModel: CharactersViewModel

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //firstViewModel = ViewModelProvider(requireActivity())[FirstViewModel::class.java]
        charsViewModel = ViewModelProvider(requireActivity())[CharactersViewModel::class.java]

        charactersAdapter = CharactersAdapter()

        binding.apply {

            charactersRecycler.apply {
                if (layoutManager == null) {
                    layoutManager = LinearLayoutManager(requireActivity())
                }
                adapter = charactersAdapter

                lifecycleScope.launchWhenCreated {
                    charactersAdapter.loadStateFlow.collect {
                        val state = it.refresh
                        prgBarCharacters.isVisible = state is LoadState.Loading
                    }
                }

            }
        }

        //getCharList(1)
        //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

    }

    private fun getCharList(page: Int) {
        firstViewModel.getCharacters(page)
        firstViewModel.characters.observe(requireActivity()) {

            binding.apply {

                charactersRecycler.apply {
                    if (layoutManager == null) {
                        layoutManager = LinearLayoutManager(requireActivity())
                    }
                    adapter = charactersAdapter
                }

            }

        }
    }

    private fun createModal(item: Character) {
        val dialog = activity?.let { Dialog(it) }
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = CharacterDetailBinding.inflate(inflater)
        dialog?.setContentView(binding.root)
        binding.name.text = item.name
        binding.gender.text = item.gender.toString()
        if (item.species != null) {
            binding.species.text = item.species.toString()
        }
        activity?.let { Glide.with(it).load(item.image).into(binding.avatar) }
        dialog?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

