package uz.infinity.mytask.presentation.vm.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.infinity.mytask.data.resources.local.database.entities.UserEntity
import uz.infinity.mytask.domain.repository.AppRepository
import uz.infinity.mytask.presentation.vm.EditViewModel
import javax.inject.Inject

@HiltViewModel
class EditViewModelImpl @Inject constructor(
    private val repository: AppRepository
) : ViewModel(), EditViewModel {
    override val changeButtonStatusLiveData = MutableLiveData<Boolean>()
    override val errorLiveData = MutableLiveData<Unit>()
    override val successLiveData = MutableLiveData<Unit>()

    override fun changeButtonStatus(bool: Boolean) {
        changeButtonStatusLiveData.value = bool
    }

    override fun updateUserInfo(data: UserEntity) {
        repository.updateUser(data).onEach {
            it.onSuccess { successLiveData.value = Unit }
            it.onFailure { errorLiveData.value = Unit }
        }.launchIn(viewModelScope)
    }
}

