package uz.teach.base.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.internal.Contexts.getApplication
import org.json.JSONObject
import retrofit2.Response
import uz.teach.base.R
import uz.teach.base.app.App
import java.net.SocketTimeoutException
import java.net.UnknownHostException


sealed class NetworkResult<T>(
    val data: T? = null, val message: String? = null, val code: Int? = null
) {
    class Success<T>(data: T?, code: Int? = null) : NetworkResult<T>(data, code = code)
    class Error<T>(message: String?, data: T? = null, code: Int? = null) : NetworkResult<T>(data, message, code)

    class Loading<T> : NetworkResult<T>()
}

fun hasInternetConnection(application: Application): Boolean {
    val connectivityManager = getApplication(application).getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        connectivityManager.activeNetwork ?: return false
    } else {
        TODO("VERSION.SDK_INT < M")
    }
    val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
    return when {
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}

fun <T> handleResponse(response: Response<T>): NetworkResult<T> {
    when {
        response.message().toString().contains("timeout") -> {
            return NetworkResult.Error("Timeout.")
        }
        response.code() == 401 -> {
            return NetworkResult.Error(App.context.getString(R.string.login_or_password_incorrect), code = 401)
        }
        response.code() == 404 -> {
            return NetworkResult.Error("Not found", code = 404)
        }
        response.code() == 403 -> {
            return NetworkResult.Error("Forbidden", code = 403)
        }
        response.code() == 500 -> {
            return NetworkResult.Error("Server xatoligi", code = 500)
        }
        response.code() == 422 -> {
            val jsonObject = response.errorBody()?.string()?.let { JSONObject(it) }
            return NetworkResult.Error(jsonObject?.getString("msg"), code = 422)
//            return NetworkResult.Error("sdvsdvsdsdv", code = 422)
        }
        response.isSuccessful -> {
            val data = response.body()
            return NetworkResult.Success(data = data, code = 200)
        }
        else -> return NetworkResult.Error(response.message())
    }
}


fun <T> catchErrors(e: Exception): NetworkResult.Error<T> {
    return when (e) {
        is SocketTimeoutException -> {
            NetworkResult.Error(App.context.getString(R.string.bad_network_message))
        }
        is UnknownHostException -> {
            NetworkResult.Error(App.context.getString(R.string.bad_network_message))
        }
        else -> {
            NetworkResult.Error(App.context.getString(R.string.onother_error) + e.message.toString())
        }
    }
}