package uz.infinity.mytask.presentation.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.widget.textChanges
import uz.infinity.mytask.R
import uz.infinity.mytask.databinding.ScreenLoginBinding
import uz.infinity.mytask.presentation.vm.LoginViewModel
import uz.infinity.mytask.presentation.vm.impl.LoginViewModelImpl
import uz.infinity.mytask.utils.scope
import uz.infinity.mytask.utils.showToast
import uz.infinity.mytask.utils.text

@AndroidEntryPoint
class LoginScreen : Fragment(R.layout.screen_login) {
    private val viewModel: LoginViewModel by viewModels<LoginViewModelImpl>()
    private val binding: ScreenLoginBinding by viewBinding(ScreenLoginBinding::bind)
    private val controller by lazy { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.openHomeScreenLiveData.observe(this, openHomeScreenObserver)
        viewModel.openRegisterScreenLiveData.observe(this, openRegisterScreenObserver)
        viewModel.openResetPasswordScreenLiveData.observe(this, openResetPasswordScreenObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        combine(
            editEmail.textChanges().map { it.length > 10 && it.endsWith("@gmail.com") },
            editPassword.textChanges().map { it.length > 3 },
            transform = { email, password -> email && password }
        ).onEach {
            viewModel.changeButtonStatus(it)
        }.launchIn(lifecycleScope)

        buttonForgotPassword.setOnClickListener { viewModel.openResetPasswordScreen() }
        buttonSignIn.setOnClickListener { viewModel.checkData(editEmail.text(), editPassword.text()) }
        buttonSignUp.setOnClickListener { viewModel.openRegisterScreen() }

        viewModel.changeButtonStatusLiveData.observe(viewLifecycleOwner, changeButtonStatusObserver)
    }

    private val errorObserver = Observer<Unit> { showToast("Wrong!") }
    private val openHomeScreenObserver = Observer<Unit> { controller.navigate(R.id.action_loginScreen_to_homeScreen) }
    private val openRegisterScreenObserver = Observer<Unit> { controller.navigate(R.id.action_loginScreen_to_registerScreen) }
    private val openResetPasswordScreenObserver = Observer<Unit> { controller.navigate(R.id.action_loginScreen_to_resetPasswordScreen) }
    private val changeButtonStatusObserver = Observer<Boolean> { binding.buttonSignIn.isEnabled = it }
}



