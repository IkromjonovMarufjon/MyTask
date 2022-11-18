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
import uz.infinity.mytask.MainActivity
import uz.infinity.mytask.R
import uz.infinity.mytask.databinding.ScreenRegisterBinding
import uz.infinity.mytask.presentation.vm.RegisterViewModel
import uz.infinity.mytask.presentation.vm.impl.RegisterViewModelImpl
import uz.infinity.mytask.utils.scope
import uz.infinity.mytask.utils.text

@AndroidEntryPoint
class RegisterScreen : Fragment(R.layout.screen_register) {
    private val viewModel: RegisterViewModel by viewModels<RegisterViewModelImpl>()
    private val binding: ScreenRegisterBinding by viewBinding(ScreenRegisterBinding::bind)
    private val controller by lazy { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.openHomeScreenLiveData.observe(this, openHomeScreenObserver)
        viewModel.closeScreenLiveData.observe(this, closeScreenObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        combine(
            editEmail.textChanges().map {
                editEmail.error = null
                it.length > 10 && it.endsWith("@gmail.com")
            },
            editPassword.textChanges().map { it.length > 4 },
            editRepeatPassword.textChanges().map { it.length > 4 },
            transform = { email, password, repeatPassword -> email && password && repeatPassword }
        ).onEach {
            val bool = editRepeatPassword.text() == editPassword.text()
            viewModel.changeButtonStatus(it && bool)
        }.launchIn(lifecycleScope)

        buttonSignUp.setOnClickListener { viewModel.insertUser(editEmail.text(), editPassword.text()) }
        buttonBack.setOnClickListener { viewModel.closeCurrentScreen() }

        viewModel.changeButtonStatusLiveData.observe(viewLifecycleOwner, changeButtonStatusObserver)
    }

    private val errorObserver = Observer<Unit> { binding.editEmail.error = "This email is busy" }
    private val openHomeScreenObserver = Observer<Unit> {
        (requireActivity() as MainActivity).clearSteak(arrayListOf(R.id.loginScreen, R.id.registerScreen), R.id.homeScreen)
    }
    private val closeScreenObserver = Observer<Unit> { controller.navigateUp() }
    private val changeButtonStatusObserver = Observer<Boolean> { binding.buttonSignUp.isEnabled = it }
}

