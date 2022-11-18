package uz.infinity.mytask.presentation.vm.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.infinity.mytask.domain.repository.AppRepository
import uz.infinity.mytask.presentation.vm.ResetPasswordViewModel
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModelImpl @Inject constructor(
    private val repository: AppRepository
) : ViewModel(), ResetPasswordViewModel {
    override val changeButtonStatusLiveData = MutableLiveData<Boolean>()
    override val createNewPasswordLiveData = MutableLiveData<Unit>()
    override val errorLiveData = MutableLiveData<Unit>()
    override val successLiveData = MutableLiveData<Unit>()
    override val closeCurrentScreenLiveData = MutableLiveData<Unit>()
    private var userID = 0

    override fun hasEmail(email: String) {
        repository.checkHasEmail(email).onEach {
            it.onFailure { errorLiveData.value = Unit }
            it.onSuccess { id ->
                userID = id
                changeButtonStatusLiveData.value = false
                createNewPasswordLiveData.value = Unit
            }
        }.launchIn(viewModelScope)
    }

    override fun changeButtonStatus(bool: Boolean) {
        changeButtonStatusLiveData.value = bool
    }

    override fun updateUserInfo(email: String, password: String) {
        repository.updateUserInfo(userID, email, password).onEach {
            successLiveData.value = Unit
            closeCurrentScreenLiveData.value = Unit
        }.launchIn(viewModelScope)
    }

    override fun closeCurrentScreen() {
        closeCurrentScreenLiveData.value = Unit
    }
}

