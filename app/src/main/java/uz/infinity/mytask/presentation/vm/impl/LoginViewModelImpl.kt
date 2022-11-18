package uz.infinity.mytask.presentation.vm.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.infinity.mytask.domain.repository.AppRepository
import uz.infinity.mytask.presentation.vm.LoginViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModelImpl @Inject constructor(
    private val repository: AppRepository
) : ViewModel(), LoginViewModel {
    override val changeButtonStatusLiveData = MutableLiveData<Boolean>()
    override val errorLiveData = MutableLiveData<Unit>()
    override val openHomeScreenLiveData = MutableLiveData<Unit>()
    override val openRegisterScreenLiveData = MutableLiveData<Unit>()
    override val openResetPasswordScreenLiveData = MutableLiveData<Unit>()

    override fun checkData(email: String, password: String) {
        repository.checkHasUser(email, password).onEach {
            it.onFailure { errorLiveData.value = Unit }
            it.onSuccess { openHomeScreenLiveData.value = Unit }
        }.launchIn(viewModelScope)
    }

    override fun changeButtonStatus(bool: Boolean) {
        changeButtonStatusLiveData.value = bool
    }

    override fun openRegisterScreen() {
        openRegisterScreenLiveData.value = Unit
    }

    override fun openResetPasswordScreen() {
        openResetPasswordScreenLiveData.value = Unit
    }
}


