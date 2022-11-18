package uz.infinity.mytask.presentation.vm

import androidx.lifecycle.LiveData
import uz.infinity.mytask.data.resources.local.database.entities.UserEntity


interface EditViewModel {
    val changeButtonStatusLiveData: LiveData<Boolean>
    val errorLiveData: LiveData<Unit>
    val successLiveData: LiveData<Unit>

    fun changeButtonStatus(bool: Boolean)
    fun updateUserInfo(data: UserEntity)
}
