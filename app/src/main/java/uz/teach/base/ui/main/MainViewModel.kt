package uz.teach.base.ui.main

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
import uz.teach.base.model.checkup.CheckupBody
import uz.teach.base.model.checkup.CheckupsResponse
import uz.teach.base.model.checkup.SaveCheckupsResponse
import uz.teach.base.utils.NetworkResult
import uz.teach.base.utils.catchErrors
import uz.teach.base.utils.handleResponse
import uz.teach.base.utils.hasInternetConnection
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    private val application: Application
) : ViewModel() {

    private val _checkupsResponse = Channel<NetworkResult<CheckupsResponse>>()
    var checkupsResponse = _checkupsResponse.receiveAsFlow()

    fun checkups(techniqueId: String?) = viewModelScope.launch {
        _checkupsResponse.send(NetworkResult.Loading())
        if (hasInternetConnection(application)) {
            try {
                val response = repository.remote.checkups(techniqueId)
                _checkupsResponse.send(handleResponse(response))
            } catch (e: Exception) {
                _checkupsResponse.send(catchErrors(e))
            }
        } else {
            _checkupsResponse.send(NetworkResult.Error(App.run { context.getString(R.string.connection_error) }))
        }
    }

    private val _saveCheckupsResponse = Channel<NetworkResult<SaveCheckupsResponse>>()
    var saveCheckupsResponse = _saveCheckupsResponse.receiveAsFlow()

    fun saveCheckups(techniqueId: String?, checkupBody: CheckupBody) = viewModelScope.launch {
        _saveCheckupsResponse.send(NetworkResult.Loading())
        if (hasInternetConnection(application)) {
            try {
                val response = repository.remote.saveCheckups(techniqueId, checkupBody)
                _saveCheckupsResponse.send(handleResponse(response))
            } catch (e: Exception) {
                _saveCheckupsResponse.send(catchErrors(e))
            }
        } else {
            _saveCheckupsResponse.send(NetworkResult.Error(App.run { context.getString(R.string.connection_error) }))
        }
    }


}