package uz.infinity.mytask.presentation.vm

import androidx.lifecycle.LiveData

interface MainViewModel {
    val loginScreenLiveData: LiveData<Unit>
    val homeScreenLiveData: LiveData<Unit>
}

