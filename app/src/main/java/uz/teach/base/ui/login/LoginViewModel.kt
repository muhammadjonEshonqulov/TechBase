package uz.teach.base.ui.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.teach.base.R
import uz.teach.base.app.App
import uz.teach.base.data.Repository
import uz.teach.base.model.login.LoginBody
import uz.teach.base.model.login.LoginResponse
import uz.teach.base.model.me.MeResponse
import uz.teach.base.utils.NetworkResult
import uz.teach.base.utils.catchErrors
import uz.teach.base.utils.handleResponse
import uz.teach.base.utils.hasInternetConnection
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository,
    private val application: Application
) : ViewModel() {
    //
    private val _loginResponse = Channel<NetworkResult<LoginResponse>>()
    var loginResponse = _loginResponse.receiveAsFlow()

    fun login(loginBody: LoginBody) = viewModelScope.launch {
        _loginResponse.send(NetworkResult.Loading())
        if (hasInternetConnection(application)) {
            try {
                val response = repository.remote.login(loginBody)
                _loginResponse.send(handleResponse(response))
            } catch (e: Exception) {
                _loginResponse.send(catchErrors(e))
            }
        } else {
            _loginResponse.send(NetworkResult.Error(App.run { context.getString(R.string.connection_error) }))
        }
    }


    private val _meResponse = Channel<NetworkResult<MeResponse>>()
    var meResponse = _meResponse.receiveAsFlow()

    fun me() = viewModelScope.launch {
        _meResponse.send(NetworkResult.Loading())
        if (hasInternetConnection(application)) {
            try {
                val response = repository.remote.me()
                _meResponse.send(handleResponse(response))
            } catch (e: Exception) {
                _meResponse.send(catchErrors(e))
            }
        } else {
            _meResponse.send(NetworkResult.Error(App.run { context.getString(R.string.connection_error) }))
        }
    }

}