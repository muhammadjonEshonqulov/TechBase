package uz.tech.base.data.network

import retrofit2.Response
import retrofit2.http.*
import uz.tech.base.model.checkup.CheckupBody
import uz.tech.base.model.checkup.CheckupsResponse
import uz.tech.base.model.checkup.SaveCheckupsResponse
import uz.tech.base.model.getCheckups.GetCheckupsResponse
import uz.tech.base.model.login.LoginBody
import uz.tech.base.model.login.LoginResponse
import uz.tech.base.model.me.MeResponse

interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body loginBody: LoginBody): Response<LoginResponse>

    @GET("auth/me")
    suspend fun me(): Response<MeResponse>

    @GET("api/checkups")
    suspend fun apiCheckups(): Response<GetCheckupsResponse>

    @GET("api/checkups/{techniqueId}")
    suspend fun checkups(@Path("techniqueId") techniqueId: String?): Response<CheckupsResponse>

    @PATCH("api/checkups/{techniqueId}")
    suspend fun saveCheckups(@Path("techniqueId") techniqueId: String?, @Body checkupBody: CheckupBody): Response<SaveCheckupsResponse>
}