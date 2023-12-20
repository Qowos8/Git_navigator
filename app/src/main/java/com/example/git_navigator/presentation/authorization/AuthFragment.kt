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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.git_navigator.R
import com.example.git_navigator.databinding.FragmentAuthorizationBinding
import com.example.git_navigator.presentation.repository_list.RepositoriesListFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : Fragment(), InputInterf {
    private lateinit var binding: FragmentAuthorizationBinding
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
        binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        binding.apply {
            authEditText.addTextChangedListener(textWatcher)
            setupButton()
            setupViewModel()
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
                inputToken = p0.toString()
            }
        }
    }

    override fun getTextInput(): String {
        val inputToken = binding.authEditText.text.toString()
        Log.d("getTextInput", inputToken)
        return inputToken
    }

    override fun openRepos(input: String, login: String) {
        val fragment = RepositoriesListFragment()
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
        val bundle = createBundle(input, login)
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.auth_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onInvalidToken() {
        val editText = binding.authEditText
        editText.error = getString(R.string.error_text)
    }

    private fun FragmentAuthorizationBinding.showLoading() {
        binding.textSign.visibility = View.INVISIBLE
        binding.load.visibility = View.VISIBLE
    }

    private fun FragmentAuthorizationBinding.setupButton() {
        binding.buttonSign.setOnClickListener {
            inputToken = getTextInput()
            Log.d("input", inputToken)
            if (isValid(inputToken)){
                showLoading()
                if(viewModel.responseAuth(inputToken)){

                }
                else{

                }
            }
            else {
                onInvalidToken()
            }
        }
    }
    private fun FragmentAuthorizationBinding.setupViewModel(){
        viewModel.user.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                name = user.login
                if (viewModel.requestData(name, inputToken)) {
                    onInvalidToken()
                } else {
                    openRepos(inputToken, name)
                    Log.d("name", name)
                }
            } else {
                onInvalidToken()
            }
        })
    }
    private fun isValid(input: String): Boolean {
        val regex = "^[a-zA-Z0-9_]+$".toRegex()
        return regex.matches(input)
    }

    companion object {
        const val USER_INPUT_KEY = "key"
        const val USER_NAME_KEY = "user"
        fun createBundle(key: String, user: String): Bundle {
            return Bundle().apply {
                putString(USER_INPUT_KEY, key)
                putString(USER_NAME_KEY, user)
            }
        }
    }
}
