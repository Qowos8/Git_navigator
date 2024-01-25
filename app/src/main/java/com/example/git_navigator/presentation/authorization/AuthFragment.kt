package com.example.git_navigator.presentation.authorization

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.git_navigator.R
import com.example.git_navigator.databinding.FragmentAuthorizationBinding
import com.example.git_navigator.presentation.main_page.MainPageFragment
import com.example.git_navigator.utils.closeInput
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthFragment : Fragment(), InputInterf {
    private lateinit var binding: FragmentAuthorizationBinding
    private val viewModel: AuthViewModel by viewModels()

    private val textWatcher = createTextWatcher()
    private lateinit var name: String
    private lateinit var inputToken: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        binding.authEditText.addTextChangedListener(textWatcher)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setupButton()
            setupViewModel()
        }
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
    private fun FragmentAuthorizationBinding.setupButton() {
        binding.buttonSign.setOnClickListener {
            inputToken = getTextInput()
            Log.d("input", inputToken)
            if (isValid(inputToken)) {
                showLoading()
                if (viewModel.responseAuth(inputToken) == true) {
                    showAlert()
                }
            } else {
                onInvalidToken()
            }
        }
    }
    private fun FragmentAuthorizationBinding.setupViewModel() {
        viewModel.user.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                name = user.login
                viewModel.apply {
                    saveUserName(name)
                    userNameSharedPref.value = (name)
                }
                openRepos(inputToken, name)
            } else {
                showAlert()
            }
        })
    }
    override fun getTextInput(): String {
        val inputToken = binding.authEditText.text.toString()
        viewModel.saveUserToken(inputToken)
        Log.d("getTextInput", inputToken)
        return inputToken
    }
    override fun openRepos(input: String, login: String) {
        val fragment = MainPageFragment()
        closeInput(requireContext(), requireView())
        val bundle = createBundle(input, login)
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.auth_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun FragmentAuthorizationBinding.showLoading() {
        binding.apply {
            textSign.visibility = View.INVISIBLE
            load.visibility = View.VISIBLE
        }
        Handler().postDelayed({
            binding.apply {
                textSign.visibility = View.VISIBLE
                load.visibility = View.GONE
            }
        }, 1000)
    }

    override fun onInvalidToken() {
        val editText = binding.authEditText
        editText.error = getString(R.string.error_text)
    }
    private fun isValid(input: String): Boolean {
        val regex = "^[a-zA-Z0-9_]+$".toRegex()
        return regex.matches(input)
    }

    private fun showAlert() {
        AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage("Error data / error code")
            .setPositiveButton("OK", null)
            .show()
    }

    companion object {
        private const val USER_INPUT_KEY = "key"
        private const val USER_NAME_KEY = "user"
        fun createBundle(key: String, user: String): Bundle {
            return Bundle().apply {
                putString(USER_INPUT_KEY, key)
                putString(USER_NAME_KEY, user)
            }
        }
    }
}
