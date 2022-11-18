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
import uz.infinity.mytask.databinding.ScreenResetPasswordBinding
import uz.infinity.mytask.presentation.vm.ResetPasswordViewModel
import uz.infinity.mytask.presentation.vm.impl.ResetPasswordViewModelImpl
import uz.infinity.mytask.utils.*

@AndroidEntryPoint
class ResetPasswordScreen : Fragment(R.layout.screen_reset_password) {
    private val viewModel: ResetPasswordViewModel by viewModels<ResetPasswordViewModelImpl>()
    private val binding: ScreenResetPasswordBinding by viewBinding(ScreenResetPasswordBinding::bind)
    private var isFoundUser = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        editEmail.addMyTextChangeListener {
            editEmail.error = null
            viewModel.changeButtonStatus((it.length > 10 && it.endsWith("@gmail.com")))
        }

        combine(
            editPassword.textChanges().map { it.length > 4 },
            editRepeatPassword.textChanges().map { it.length > 4 },
            transform = { password, repeatPassword -> password && repeatPassword }
        ).onEach {
            val bool = editRepeatPassword.text() == editPassword.text()
            myLog((it && bool).toString())
            viewModel.changeButtonStatus(it && bool)
        }.launchIn(lifecycleScope)

        buttonBack.setOnClickListener { viewModel.closeCurrentScreen() }
        buttonSignUp.setOnClickListener {
            if (isFoundUser) viewModel.updateUserInfo(editEmail.text(), editPassword.text())
            else viewModel.hasEmail(editEmail.text())
        }

        viewModel.changeButtonStatusLiveData.observe(viewLifecycleOwner, changeButtonStatusObserver)
        viewModel.createNewPasswordLiveData.observe(viewLifecycleOwner, createNewPasswordObserver)
        viewModel.errorLiveData.observe(viewLifecycleOwner, errorObserver)
        viewModel.successLiveData.observe(viewLifecycleOwner, successObserver)
        viewModel.closeCurrentScreenLiveData.observe(viewLifecycleOwner, closeCurrentScreenObserver)
    }

    private val changeButtonStatusObserver = Observer<Boolean> { binding.buttonSignUp.isEnabled = it }
    private val createNewPasswordObserver = Observer<Unit> {
        binding.apply {
            editEmail.isEnabled = false
            buttonSignUp.setText(R.string.set_new_password)
            editPassword.visible()
            editRepeatPassword.visible()
            isFoundUser = true
        }
    }
    private val errorObserver = Observer<Unit> { binding.editEmail.error = "This email was not found" }
    private val successObserver = Observer<Unit> { showToast("Success!!!") }
    private val closeCurrentScreenObserver = Observer<Unit> { findNavController().navigateUp() }
}

