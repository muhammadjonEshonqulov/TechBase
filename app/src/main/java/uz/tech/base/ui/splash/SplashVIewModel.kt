package uz.tech.base.ui.splash

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.tech.base.data.Repository
import javax.inject.Inject

@HiltViewModel
class SplashVIewModel @Inject constructor(
    val repository: Repository,

    ) : ViewModel() {


}