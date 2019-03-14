package com.prince.smsreader.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prince.smsreader.R
import com.prince.smsreader.model.Messages

class MessageAdapter(private val context: Context) :
    ListAdapter<Messages, MessageAdapter.MessageViewHolder>(DIFF_UTIL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.MessageViewHolder {

        val dataBindingUtil: com.prince.smsreader.databinding.ItemMessageBinding =
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_message, parent, false)
        return MessageViewHolder(dataBindingUtil)
    }


    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = getItem(position)
        message?.let {
            holder.bind(it)
        }
    }

    inner class MessageViewHolder(@NonNull private val binding: com.prince.smsreader.databinding.ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Messages) {
            binding.model = model
        }
    }


    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Messages>() {
            override fun areItemsTheSame(oldItem: Messages, newItem: Messages): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Messages, newItem: Messages): Boolean {
                return newItem == oldItem
            }
        }
    }
}