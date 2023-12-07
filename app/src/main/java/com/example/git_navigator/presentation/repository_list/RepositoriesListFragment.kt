package com.example.git_navigator.presentation.repository_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.git_navigator.data.network.RetrofitBuilder
import com.example.git_navigator.databinding.ListRepositoryBinding
import com.example.git_navigator.presentation.authorization.AuthViewModelFactory
import com.example.git_navigator.presentation.authorization.AuthViewModel

class RepositoriesListFragment : Fragment() {
    private lateinit var binding: ListRepositoryBinding
    private lateinit var adapter: listAdapter
    private lateinit var viewModel: AuthViewModel
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListRepositoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val input = arguments?.getString("key")
        val name = arguments?.getString("user")
        Log.d("replistfragment", "$input")
        Log.d("replistName", "$name")

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView = binding.recycler
        recyclerView.layoutManager = layoutManager
        viewModel = ViewModelProvider(
            this,
            AuthViewModelFactory(RetrofitBuilder.create(input!!))
        )[AuthViewModel::class.java]
        viewModel.repList.observe(viewLifecycleOwner) { repository ->
            adapter.setData(repository!!)
            recyclerView.adapter = adapter
        }
        viewModel.response(name!!)
        adapter = listAdapter(requireContext(), name, input)
    }
}
