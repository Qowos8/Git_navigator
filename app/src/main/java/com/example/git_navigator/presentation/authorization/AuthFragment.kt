package com.example.git_navigator.presentation.authorization

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.git_navigator.R
import com.example.git_navigator.data.network.RetrofitBuilder
import com.example.git_navigator.databinding.AuthFragmentBinding
import com.example.git_navigator.presentation.repository_list.RepositoriesListFragment

class AuthFragment : Fragment(), inputInterface {
    private lateinit var binding: AuthFragmentBinding
    private lateinit var viewModel: AuthViewModel
    private var token = ""
    private var textWatcher = createTextWatcher()
    private lateinit var name: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.auth_fragment, container, false)
        val button = binding.button
        binding.logEdit.addTextChangedListener(textWatcher)
        button.setOnClickListener {
            token = getTextInput()
            Log.d("input", "$token")
            viewModel = ViewModelProvider(
                this,
                AuthFactory(RetrofitBuilder.create(token))
            )[AuthViewModel::class.java]
            val user = viewModel.responseAuth(token)
            Log.d("user", "$user")
            viewModel.user.observe(viewLifecycleOwner, Observer { user ->
                if (user != null) {
                    Log.d("users", "${user.id}, ${user.name}, ${user.email}, ${user.login}")
                    name = user.login
                    viewModel.response(token, name)
                    success(token)
                    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view?.windowToken, 0)
                    Log.d("name", "$name")
                }
            })
        }


        return binding.root
    }

    private fun createTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                token = p0.toString()
            }
        }
    }

    override fun getTextInput(): String {
        val inputText: EditText = binding.logEdit
        val inputToken = inputText.text.toString()
        Log.d("getTextInput", inputToken)
        return inputToken
    }

    private fun success(input: String) {
        val fragment = RepositoriesListFragment()
        val bundle = Bundle()
        bundle.putString("key", "$input")
        bundle.putString("user", name)
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_containerr, fragment)
            .commit()

    }
}