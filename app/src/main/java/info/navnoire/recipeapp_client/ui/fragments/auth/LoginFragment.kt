package info.navnoire.recipeapp_client.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import info.navnoire.recipeapp_client.databinding.FragmentLoginBinding
import info.navnoire.recipeapp_client.networking.Result
import info.navnoire.recipeapp_client.utils.enable
import info.navnoire.recipeapp_client.utils.visible
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private val viewModel: AuthViewModel by activityViewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressbar.visible(false)
        binding.buttonLogin.enable(false)

        viewModel.signinResponse.observe(viewLifecycleOwner, {
            binding.progressbar.visible(it is Result.Loading)
            when (it) {
                is Result.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveAccessTokens(
                            it.data.accessToken,
                            it.data.refreshToken
                        )
                    }
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_LONG).show()
                }
                is Result.Loading -> {

                }
            }
        })
        binding.editTextPassword.addTextChangedListener {
            val username = binding.editTextUsername.text.toString().trim()
            binding.buttonLogin.enable(username.isNotEmpty() && it.toString().trim().isNotEmpty())
        }

        binding.buttonLogin.setOnClickListener {
            signin()
        }
    }

    private fun signin() {
        val username = binding.editTextUsername.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()
        viewModel.signin(username = username, password = password)
    }
}