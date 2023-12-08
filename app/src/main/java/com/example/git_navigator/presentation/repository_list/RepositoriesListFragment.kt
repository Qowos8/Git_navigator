package com.example.git_navigator.presentation.repository_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.git_navigator.data.network.RetrofitBuilder
import com.example.git_navigator.databinding.ListRepositoryBinding
import com.example.git_navigator.presentation.authorization.AuthViewModel
import com.example.git_navigator.presentation.authorization.AuthViewModelFactory

class RepositoriesListFragment : Fragment() {
    private lateinit var binding: ListRepositoryBinding
    private lateinit var adapter: listAdapter
    private lateinit var input: String
    private val viewModel: AuthViewModel by viewModels {AuthViewModelFactory(RetrofitBuilder.create(input))}
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListRepositoryBinding.inflate(layoutInflater)
        val layoutParams = setLayoutParams()
        binding.frameL.layoutParams = layoutParams
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        input = arguments?.getString("key").toString()
        val name = arguments?.getString("user")
        Log.d("replistfragment", input)
        Log.d("replistName", "$name")

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView = binding.recycler
        recyclerView.layoutManager = layoutManager

        viewModel.repList.observe(viewLifecycleOwner) { repository ->
            adapter.setData(repository!!)
            recyclerView.adapter = adapter
        }
        viewModel.response(name!!)
        adapter = listAdapter(requireContext(), name, input)
    }

    private fun setLayoutParams() = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )
}
