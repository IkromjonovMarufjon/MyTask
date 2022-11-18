package uz.infinity.mytask.presentation.vm.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.infinity.mytask.domain.repository.AppRepository
import uz.infinity.mytask.presentation.vm.RegisterViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModelImpl @Inject constructor(
    private val repository: AppRepository
) : ViewModel(), RegisterViewModel {
    override val changeButtonStatusLiveData = MutableLiveData<Boolean>()
    override val errorLiveData = MutableLiveData<Unit>()
    override val closeScreenLiveData = MutableLiveData<Unit>()
    override val openHomeScreenLiveData = MutableLiveData<Unit>()

    override fun changeButtonStatus(bool: Boolean) {
        changeButtonStatusLiveData.value = bool
    }

    override fun insertUser(email: String, password: String) {
        repository.insertNewUser(email, password).onEach {
            it.onSuccess { openHomeScreenLiveData.value = Unit }
            it.onFailure { errorLiveData.value = Unit }
        }.launchIn(viewModelScope)
    }

    override fun closeCurrentScreen() {
        closeScreenLiveData.value = Unit
    }
}



