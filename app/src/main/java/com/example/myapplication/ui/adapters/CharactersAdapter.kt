package com.example.myapplication.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.data.models.CharResponse
import com.example.myapplication.data.models.Character
import com.example.myapplication.databinding.ItemCharListBinding
import javax.inject.Inject

class CharactersAdapter @Inject() constructor() :
    PagingDataAdapter<Character, CharactersAdapter.ViewHolder>(differCallback) {

    inner class ViewHolder(private val binding: ItemCharListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Character) {
            binding.apply {
                name.text = item.name
                gender.text = item.gender.toString()
                var requestOptions = RequestOptions()
                requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(16))

                Glide.with(avatar.context).load(item.image).apply(requestOptions).into(avatar)
                characterCard.setOnClickListener {
                    onItemClickListener?.let { it(item) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCharListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bind(getItem(position)!!)
    }


    companion object {
        val differCallback = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    private var onItemClickListener: ((Character) -> Unit)? = null

    fun setOnItemClickListener(listener: (Character) -> Unit) {
        onItemClickListener = listener
    }

}


