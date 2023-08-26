package uz.teach.base.data

import retrofit2.Response
import uz.teach.base.data.network.ApiService
import uz.teach.base.model.checkup.CheckupBody
import uz.teach.base.model.checkup.CheckupsResponse
import uz.teach.base.model.checkup.SaveCheckupsResponse
import uz.teach.base.model.login.LoginBody
import uz.teach.base.model.login.LoginResponse
import uz.teach.base.model.me.MeResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun login(loginBody: LoginBody): Response<LoginResponse> {
        return apiService.login(loginBody)
    }

    suspend fun me(): Response<MeResponse> {
        return apiService.me()
    }
    suspend fun checkups(techniqueId: String?): Response<CheckupsResponse> {
        return apiService.checkups(techniqueId)
    }

    suspend fun saveCheckups(techniqueId: String?, checkupBody: CheckupBody): Response<SaveCheckupsResponse> {
        return apiService.saveCheckups(techniqueId, checkupBody)
    }

}