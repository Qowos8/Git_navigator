package com.example.git_navigator.presentation.repository_list

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.git_navigator.data.network.Repository
import com.example.git_navigator.databinding.FragmentRepoListBinding
import com.example.git_navigator.presentation.authorization.AuthState
import com.example.git_navigator.presentation.authorization.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoriesListFragment : Fragment() {
    private lateinit var binding: FragmentRepoListBinding
    private var repo: List<Repository> = emptyList()
    private lateinit var pref: SharedPreferences

    private val viewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        pref = requireContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        binding = FragmentRepoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = pref.getString("token", "")
        val login = pref.getString("login", "")
        Log.d("replistName", "$login")
        Log.d("replistfragment", token!!)
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val listAdapter = ListAdapter(login!!)

        binding.recycler.apply {
            layoutManager = linearLayoutManager
            adapter = listAdapter
        }
        viewModel.repList.observe(viewLifecycleOwner) { repository ->
            listAdapter.setData(repository!!)
        }
        viewModel.requestData(login, token)
    }

    fun setAdapter(state: AuthState) = when (state) {
        is AuthState.SuccessRepos -> {
            repo = state.repositories.take(10)
            Log.d("repo", "$repo")
        }

        else -> {
            Log.d("setAdapter", "fail")
        }
    }
}
