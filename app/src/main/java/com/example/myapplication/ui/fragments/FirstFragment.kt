package com.example.myapplication.ui.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.clearFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.data.models.Character
import com.example.myapplication.databinding.CharacterDetailBinding
import com.example.myapplication.databinding.FragmentFirstBinding
import com.example.myapplication.ui.adapters.CharactersAdapter
import com.example.myapplication.ui.adapters.LoadMoreAdapter
import com.example.myapplication.ui.viewModels.main.CharactersViewModel
import com.example.myapplication.utils.ApiState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FirstFragment : Fragment() {
    /* private lateinit var charactersAdapter: CharactersAdapter
     private lateinit var firstViewModel: FirstViewModel
     private lateinit var charsViewModel: CharactersViewModel

     private var _binding: FragmentFirstBinding? = null
     private val binding get() = _binding!!*/
    @Inject
    lateinit var charAdapter: CharactersAdapter
    private lateinit var binding: FragmentFirstBinding
    private val charViewModel: CharactersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFirstBinding.inflate(layoutInflater, container, false)
        // _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            charViewModel.getCharList(1)
            lifecycleScope.launchWhenStarted {
                charViewModel.postStateFlow.collect { it ->

                    when (it) {
                        is ApiState.Loading -> {
                        }
                        is ApiState.Failure -> {
                           Log.d("test","test1")
                        }
                        is ApiState.Success<*> -> {
                            Log.d("test","test2")
                           // it.results
                           /* binding.rvExpenseList.isVisible = true
                            val result = it.result as AlertsResponseClass
                            alertsList = result.alerts
                            postAdapter.setData(result.alerts)
                            postAdapter.notifyDataSetChanged()
                            for (i in 0 until result.alerts.size) {
                                //saving to db
                                alertViewModel.insert(result.alerts.get(i))
                            }*/
                        }
                        is ApiState.Empty -> {

                        }
                    }
                    Log.d("test",it.toString())
                   //charAdapter.submitData(it)
                }
            }

            lifecycleScope.launchWhenCreated {
                charViewModel.charList.collect {
                    charAdapter.submitData(it)
                }
            }

            charAdapter.setOnItemClickListener {
                createModal(it)
            }

            lifecycleScope.launchWhenCreated {
                charAdapter.loadStateFlow.collect {
                    val state = it.refresh
                    prgBarCharacters.isVisible = state is LoadState.Loading
                }
            }


            charactersRecycler.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = charAdapter
            }

            charactersRecycler.adapter = charAdapter.withLoadStateFooter(
                LoadMoreAdapter {
                    charAdapter.retry()
                }
            )
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

}

