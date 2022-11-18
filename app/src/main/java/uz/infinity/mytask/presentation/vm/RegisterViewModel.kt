package uz.infinity.mytask.presentation.vm

import androidx.lifecycle.LiveData

interface RegisterViewModel {
    val changeButtonStatusLiveData: LiveData<Boolean>
    val errorLiveData: LiveData<Unit>
    val closeScreenLiveData: LiveData<Unit>
    val openHomeScreenLiveData: LiveData<Unit>

    fun changeButtonStatus(bool : Boolean)
    fun insertUser(email: String, password: String)
    fun closeCurrentScreen()
}

