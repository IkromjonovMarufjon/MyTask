package uz.infinity.mytask.presentation.vm

import androidx.lifecycle.LiveData
import uz.infinity.mytask.data.resources.local.database.entities.UserEntity

interface HomeViewModel {
    val logOutLiveData: LiveData<Unit>
    val clickHomeButtonLiveData: LiveData<Unit>
    val allUserLiveData: LiveData<List<UserEntity>>
    val showEventDialogLiveData: LiveData<UserEntity>
    val showEditDialogLiveData: LiveData<UserEntity>

    fun logOutUser()
    fun loadUsers()
    fun clickHomeButton()
    fun showEventDialog(data: UserEntity)
    fun showEditDialog(data: UserEntity)
    fun deleteUser(data: UserEntity)
    fun undoTask()
}