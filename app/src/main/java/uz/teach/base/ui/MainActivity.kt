package uz.teach.base.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.ActivityResult
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import dagger.hilt.android.AndroidEntryPoint
import uz.teach.base.R
import uz.teach.base.basethings.LoaderInterface
import uz.teach.base.databinding.ActivityMainBinding
import uz.teach.base.utils.lg

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), LoaderInterface {

    lateinit var binding: ActivityMainBinding

    private val MY_REQUEST_CODE = 17326


    private val appUpdateManager by lazy { AppUpdateManagerFactory.create(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        checkUpdate()
    }

    private fun checkUpdate() {
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateManager.registerListener(listener)
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, this, MY_REQUEST_CODE)
                Toast.makeText(this, "Yes update version", Toast.LENGTH_SHORT).show()
            } else if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                Toast.makeText(this, "Yes update version", Toast.LENGTH_SHORT).show()
                appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, this, MY_REQUEST_CODE)
            }

        }.addOnFailureListener {

        }
    }

    private val listener: InstallStateUpdatedListener = InstallStateUpdatedListener { installState ->
        if (installState.installStatus() == InstallStatus.DOWNLOADED) {
            showSnackBarForCompleteUpdate()
        }
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, this, MY_REQUEST_CODE)
            }
        }
    }

    fun openPermissionSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MY_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                }

                Activity.RESULT_CANCELED -> {
//                    checkUpdate()
//                    snackBar(binding, "RESULT_CANCELED")
                }

                ActivityResult.RESULT_IN_APP_UPDATE_FAILED -> {
//                    checkUpdate()
//                    snackBar(binding,"RESULT_IN_APP_UPDATE_FAILED" )
                }
            }
        }
    }

    private fun showSnackBarForCompleteUpdate() {
        val snackbar = Snackbar.make(binding.root, "New app is ready!", Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("Install") { view: View? ->
            appUpdateManager.completeUpdate()
        }
        snackbar.setActionTextColor(ContextCompat.getColor(binding.root.context, R.color.cl_color_primary))
        snackbar.show()
    }

    override fun showLoader() {
        try {
            binding.mainLoader.visibility = View.VISIBLE
        } catch (e: Exception) {
            lg("Main activity " + e.message.toString())
        }
    }

    override fun closeLoader() {
        try {
            binding.mainLoader.visibility = View.GONE
        } catch (e: Exception) {
            lg("Main activity " + e.message.toString())
        }
    }
}