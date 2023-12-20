package com.example.git_navigator.presentation.repository_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.git_navigator.data.network.Repository
import com.example.git_navigator.databinding.FragmentRepoListBinding
import com.example.git_navigator.presentation.authorization.AuthFragment.Companion.USER_INPUT_KEY
import com.example.git_navigator.presentation.authorization.AuthFragment.Companion.USER_NAME_KEY
import com.example.git_navigator.presentation.authorization.AuthState
import com.example.git_navigator.presentation.authorization.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait

@AndroidEntryPoint
class RepositoriesListFragment : Fragment() {
    private lateinit var binding: FragmentRepoListBinding
    private var repo: List<Repository> = emptyList()
    private val input: String by lazy {
        requireArguments().getString(USER_INPUT_KEY).toString()
    }

    private val viewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = requireArguments().getString(USER_NAME_KEY)
        Log.d("replistfragment", input)
        Log.d("replistName", "$name")
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val listAdapter = ListAdapter(name!!)

        binding.recycler.apply {
            layoutManager = linearLayoutManager
            adapter = listAdapter
        }
        viewModel.repList.observe(viewLifecycleOwner) { repository ->
            listAdapter.setData(repository!!)
        }
        viewModel.requestData(name, input)
    }
    fun setAdapter(state: AuthState) = when(state){
        is AuthState.SuccessRepos -> {
            repo = state.repositories.take(10)
            Log.d("repo", "$repo")
        }

        else -> {
            Log.d("setAdapter", "fail")
        }
    }
}
