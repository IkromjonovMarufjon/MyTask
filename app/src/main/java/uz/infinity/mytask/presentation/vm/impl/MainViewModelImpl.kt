package uz.infinity.mytask.presentation.vm.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.infinity.mytask.data.model.ScreenEnum
import uz.infinity.mytask.domain.repository.AppRepository
import uz.infinity.mytask.presentation.vm.MainViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModelImpl @Inject constructor(
    repository: AppRepository
) : ViewModel(), MainViewModel {
    override val loginScreenLiveData = MutableLiveData<Unit>()
    override val homeScreenLiveData = MutableLiveData<Unit>()

    init {
        when (repository.getStartScreenEnum()) {
            ScreenEnum.LOGIN -> loginScreenLiveData.value = Unit
            ScreenEnum.HOME -> homeScreenLiveData.value = Unit
        }
    }
}

