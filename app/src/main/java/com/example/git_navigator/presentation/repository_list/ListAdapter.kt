package com.example.git_navigator.presentation.repository_list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.git_navigator.data.network.Repository
import com.example.git_navigator.databinding.FragmentRecyclerBinding

class ListAdapter(val name: String): RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    private var items: List<Repository> = emptyList()
    fun setData(data: List<Repository>) {
        this.items = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentRecyclerBinding.inflate(inflater, parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
    class ListViewHolder(private val binding: FragmentRecyclerBinding): RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.nameRep
        val language: TextView = binding.language
        val review: TextView = binding.review
        fun bind(item: Repository) {
            binding.apply {
                nameRep.text = item.name
                language.text = item.language
                review.text = item.description
            }
        }
    }
}