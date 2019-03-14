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
import com.prince.smsreader.model.SMSMessages

class SMSAdapter(private val context: Context) : ListAdapter<SMSMessages, SMSAdapter.SMSViewHolder>(DIFF_UTIL) {

    private var messageAdapter: MessageAdapter? = null

    private val recyclerViewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SMSAdapter.SMSViewHolder {

        val dataBindingUtil: com.prince.smsreader.databinding.ItemSmsBinding =
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_sms, parent, false)
        return SMSViewHolder(dataBindingUtil)
    }


    override fun onBindViewHolder(holder: SMSAdapter.SMSViewHolder, position: Int) {
        val sms = getItem(position)
        sms?.let {
            holder.bind(it)
        }
    }

    inner class SMSViewHolder(@NonNull private val binding: com.prince.smsreader.databinding.ItemSmsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            messageAdapter = MessageAdapter(context)
            binding.rvMessages.setRecycledViewPool(recyclerViewPool)
            binding.rvMessages.adapter = messageAdapter
        }

        fun bind(model: SMSMessages) {
            binding.model = model
            messageAdapter?.submitList(model.messages)
        }
    }


    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<SMSMessages>() {
            override fun areItemsTheSame(oldItem: SMSMessages, newItem: SMSMessages): Boolean {
                return oldItem.hour == newItem.hour
            }

            override fun areContentsTheSame(oldItem: SMSMessages, newItem: SMSMessages): Boolean {
                return newItem == oldItem
            }
        }
    }
}