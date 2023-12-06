package com.example.git_navigator.presentation.repository_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.git_navigator.R
import com.example.git_navigator.data.network.RetrofitBuilder
import com.example.git_navigator.databinding.ListRepositoryBinding
import com.example.git_navigator.presentation.authorization.AuthFactory
import com.example.git_navigator.presentation.authorization.AuthViewModel
import kotlinx.coroutines.launch

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
        binding = DataBindingUtil.inflate(inflater, R.layout.list_repository, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val input = arguments?.getString("key")
        val name = arguments?.getString("user")
        Log.d("replistfragment", "$input")
        Log.d("replistName", "$name")

        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView = binding.recycler
        recyclerView.layoutManager = layoutManager
        viewModel = ViewModelProvider(
            this,
            AuthFactory(RetrofitBuilder.create(input!!))
        )[AuthViewModel::class.java]
        viewModel.repList.observe(viewLifecycleOwner) { repository ->
            adapter.setData(repository!!)
            recyclerView.adapter = adapter
        }
        val data = viewModel.response(input, name!!)
        Log.d("data", "$data")
        adapter = listAdapter(requireContext(), name, input)
    }
}
