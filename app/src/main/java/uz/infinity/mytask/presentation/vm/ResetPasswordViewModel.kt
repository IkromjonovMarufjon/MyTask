package uz.infinity.mytask.presentation.vm

import androidx.lifecycle.LiveData

interface ResetPasswordViewModel {
    val changeButtonStatusLiveData: LiveData<Boolean>
    val createNewPasswordLiveData: LiveData<Unit>
    val errorLiveData: LiveData<Unit>
    val successLiveData: LiveData<Unit>
    val closeCurrentScreenLiveData: LiveData<Unit>

    fun hasEmail(email: String)
    fun changeButtonStatus(bool: Boolean)
    fun updateUserInfo(email: String, password: String)
    fun closeCurrentScreen()
}

