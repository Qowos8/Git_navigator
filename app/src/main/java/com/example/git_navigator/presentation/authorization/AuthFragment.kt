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
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.git_navigator.R
import com.example.git_navigator.databinding.AuthFragmentBinding
import com.example.git_navigator.presentation.repository_list.RepositoriesListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment(), InputInterf {
    private lateinit var binding: AuthFragmentBinding
    private lateinit var name: String
    private lateinit var inputToken: String
    private val viewModel: AuthViewModel by viewModels()
    private val textWatcher = createTextWatcher()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = AuthFragmentBinding.inflate(layoutInflater)
        val layoutParams = setLayoutParams()
        binding.apply {
            authContainer.layoutParams = layoutParams
            authEditText.addTextChangedListener(textWatcher)
        }
        binding.transition()
        return binding.root
    }

    private fun createTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                inputToken = p0.toString()
            }
        }
    }

    override fun getTextInput(): String {
        val inputText: EditText = binding.authEditText
        val inputToken = inputText.text.toString()
        Log.d("getTextInput", inputToken)
        return inputToken
    }

    override fun openRepos(input: String) {
        val fragment = RepositoriesListFragment()
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
        val bundle = createBundle(input, name)
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.auth_container, fragment)
            .commit()

    }

    private fun setLayoutParams() = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )

    override fun invalidToken() {
        val editText = binding.authEditText
        editText.error = getString(R.string.error_text)
    }
    companion object {
        const val KEY_STRING = "key"
        const val USER_KEY = "user"
        fun createBundle(key: String, user: String): Bundle {
            return Bundle().apply {
                putString(KEY_STRING, key)
                putString(USER_KEY, user)
            }
        }
    }
    private fun showLoading(button: TextView, load: ProgressBar){
        button.visibility = View.INVISIBLE
        load.visibility = View.VISIBLE
    }
    private fun AuthFragmentBinding.transition(){
        binding.buttonSign.setOnClickListener {
            inputToken = getTextInput()
            Log.d("input", inputToken)
            viewModel.responseAuth(inputToken)
            showLoading(binding.textSign, binding.load)

            viewModel.user.observe(viewLifecycleOwner, Observer { user ->
                if (user != null) {
                    name = user.login
                    if (viewModel.requestData(name, inputToken)) {
                        invalidToken()
                    } else {
                        openRepos(inputToken)
                        Log.d("name", name)
                    }
                } else {
                    invalidToken()
                }
            })
        }
    }
}
