package uz.tech.base.ui.login

import android.app.ProgressDialog
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.tech.base.R
import uz.tech.base.basethings.BaseFragment
import uz.tech.base.databinding.FragmentLoginBinding
import uz.tech.base.model.login.LoginBody
import uz.tech.base.utils.NetworkResult
import uz.tech.base.utils.blockClickable
import uz.tech.base.utils.hideKeyboard
import uz.tech.base.utils.lg
import uz.tech.base.utils.Prefs
import uz.tsul.mobile.utils.collectLA
import uz.tsul.mobile.utils.navigateSafe
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate),
    View.OnClickListener {

    private val vm: LoginViewModel by viewModels()
    var progressDialog: ProgressDialog? = null

    @Inject
    lateinit var prefs: Prefs


    override fun onViewCreatedd(view: View, savedInstanceState: Bundle?) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding.loginBtn.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        v.blockClickable()
        when (v) {
            binding.loginBtn -> {
                hideKeyboard()
                val login = binding.loginAuth.text.toString()
                val password = binding.passwordAuth.text.toString()
                if (login.isNotEmpty() && password.isNotEmpty()) {
                    login(LoginBody(login, password))
                } else {
                    if (login.isEmpty()) {
                        binding.loginAuth.error = "Loginingizni kiriting"
                    }
                    if (password.isEmpty()) {
                        binding.passwordAuth.error = "Parolni kiriting"
                    }
                }
            }

        }
    }

    private fun login(loginBody: LoginBody) {
        vm.login(loginBody)
        vm.loginResponse.collectLA(lifecycleScope) {
            when (it) {
                is NetworkResult.Loading -> {
                    showLoader()
                }

                is NetworkResult.Success -> {
                    closeLoader()
                    it.data?.token?.let {
                        prefs.save(prefs.token, it)
                        me()
                    }
                }

                is NetworkResult.Error -> {
                    closeLoader()
                    snackBar(it.message.toString())
                    lg(it.message.toString())
                }

                else -> {}
            }
        }
    }

    private fun me() {
        vm.me()
        vm.meResponse.collectLA(lifecycleScope) {
            when (it) {
                is NetworkResult.Loading -> {
                    showLoader()
                }

                is NetworkResult.Success -> {
                    closeLoader()
                    it.data?.fullName?.let {
                        prefs.save(prefs.full_name, it)
                    }
                    it.data?.login?.let {
                        prefs.save(prefs.login, it)
                    }
                    it.data?._id?.let {
                        prefs.save(prefs.user_id, it)
                    }

                    findNavController().navigateSafe(R.id.action_loginFragment_to_mainFragment)
                }

                is NetworkResult.Error -> {
                    closeLoader()
                    snackBar(it.message.toString())
                }

                else -> {}
            }
        }
    }
}

