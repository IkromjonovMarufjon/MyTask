package uz.infinity.mytask.presentation.vm.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.infinity.mytask.data.resources.local.database.entities.UserEntity
import uz.infinity.mytask.domain.repository.AppRepository
import uz.infinity.mytask.presentation.vm.HomeViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    private val repository: AppRepository
) : ViewModel(), HomeViewModel {
    override val logOutLiveData = MutableLiveData<Unit>()
    override val clickHomeButtonLiveData = MutableLiveData<Unit>()
    override val allUserLiveData = MutableLiveData<List<UserEntity>>()
    override val showEventDialogLiveData = MutableLiveData<UserEntity>()
    override val showEditDialogLiveData = MutableLiveData<UserEntity>()

    private val list = ArrayList<UserEntity>()
    private var job: Job? = null

    init {
       loadUsers()
    }

    override fun loadUsers() {
        repository.getAllUsers().onEach {
            list.clear()
            list.addAll(it)
            allUserLiveData.value = it
        }.launchIn(viewModelScope)
    }

    override fun logOutUser() {
        repository.logOutUser()
        logOutLiveData.value = Unit
    }

    override fun clickHomeButton() {
        clickHomeButtonLiveData.value = Unit
    }

    override fun showEventDialog(data: UserEntity) {
        showEventDialogLiveData.value = data
    }

    override fun showEditDialog(data: UserEntity) {
        showEditDialogLiveData.value = data
    }

    override fun deleteUser(data: UserEntity) {
        val worker = ArrayList<UserEntity>(list)
        worker.remove(data)
        allUserLiveData.value = worker
        job = viewModelScope.launch {
            delay(2500)
            list.remove(data)
            repository.deleteUser(data).launchIn(viewModelScope)
        }
    }

    override fun undoTask() {
        job?.cancel()
        allUserLiveData.value = list
    }
}

