package uz.infinity.mytask.presentation.vm

import androidx.lifecycle.LiveData

interface LoginViewModel {
    val changeButtonStatusLiveData: LiveData<Boolean>
    val errorLiveData: LiveData<Unit>
    val openHomeScreenLiveData: LiveData<Unit>
    val openRegisterScreenLiveData: LiveData<Unit>
    val openResetPasswordScreenLiveData: LiveData<Unit>

    fun checkData(email: String, password: String)
    fun changeButtonStatus(bool : Boolean)
    fun openRegisterScreen()
    fun openResetPasswordScreen()
}

