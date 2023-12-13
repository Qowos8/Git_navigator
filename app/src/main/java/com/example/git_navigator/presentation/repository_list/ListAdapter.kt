package com.example.git_navigator.presentation.repository_list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.git_navigator.data.network.Repository
import com.example.git_navigator.databinding.ListBlocksBinding

class ListAdapter(val name: String): RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    private var items: List<Repository>? = null
    fun setData(data: List<Repository>) {
        this.items = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListBlocksBinding.inflate(inflater, parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.binding.apply{
            val item = items?.get(position)
            nameRep.text = item?.name
            language.text = item?.language
            review.text = item?.description

        }
    }

    override fun getItemCount(): Int = items?.size ?: 0
    class ListViewHolder(val binding: ListBlocksBinding): RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.nameRep
        val language: TextView = binding.language
        val review: TextView = binding.review
    }

    /*override suspend fun loadDataFromNetwork(@Named("authToken") userToken: String, holder: ListViewHolder) {
        val retrofit = RetrofitBuilder.create(userToken)
        val response = retrofit.getUserRepos(name)
        if(response.isSuccessful){
            val body = response.body()
            if (body != null) {
                for (i in 0..10) {
                    val repositoryList = body[i]
                    holder.binding.apply {
                        nameRep.text = repositoryList.name
                        language.text = repositoryList.language
                        review.text = repositoryList.description
                    }
                }

            }
            else{
                Log.d("loadfromNetwork", "body = null")
            }
        }
        else{
            Log.d("loadfromNetwork", "response is not successful")
        }
    }*/
}