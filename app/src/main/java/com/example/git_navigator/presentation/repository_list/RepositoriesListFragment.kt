package com.example.git_navigator.presentation.repository_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.git_navigator.databinding.ListRepositoryBinding
import com.example.git_navigator.presentation.authorization.AuthFragment.Companion.KEY_STRING
import com.example.git_navigator.presentation.authorization.AuthFragment.Companion.USER_KEY
import com.example.git_navigator.presentation.authorization.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoriesListFragment : Fragment() {
    private lateinit var binding: ListRepositoryBinding
    private val input: String by lazy { arguments?.getString(KEY_STRING).toString() }
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListRepositoryBinding.inflate(layoutInflater)
        val layoutParams = setLayoutParams()
        binding.frameContainer.layoutParams = layoutParams
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = arguments?.getString(USER_KEY)
        Log.d("replistfragment", input)
        Log.d("replistName", "$name")
        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
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

    private fun setLayoutParams() = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )
}
