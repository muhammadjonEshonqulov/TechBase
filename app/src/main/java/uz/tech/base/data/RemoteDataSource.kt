package uz.tech.base.data

import retrofit2.Response
import uz.tech.base.data.network.ApiService
import uz.tech.base.model.checkup.CheckupBody
import uz.tech.base.model.checkup.CheckupsResponse
import uz.tech.base.model.checkup.SaveCheckupsResponse
import uz.tech.base.model.getCheckups.GetCheckupsResponse
import uz.tech.base.model.login.LoginBody
import uz.tech.base.model.login.LoginResponse
import uz.tech.base.model.me.MeResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun login(loginBody: LoginBody): Response<LoginResponse> {
        return apiService.login(loginBody)
    }

    suspend fun me(): Response<MeResponse> {
        return apiService.me()
    }

    suspend fun apiCheckups(): Response<GetCheckupsResponse> {
        return apiService.apiCheckups()
    }
    suspend fun checkups(techniqueId: String?): Response<CheckupsResponse> {
        return apiService.checkups(techniqueId)
    }

    suspend fun saveCheckups(techniqueId: String?, checkupBody: CheckupBody): Response<SaveCheckupsResponse> {
        return apiService.saveCheckups(techniqueId, checkupBody)
    }

}