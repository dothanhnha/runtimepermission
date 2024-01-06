package com.example.myruntimepermissionexample

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    ///permissions request
    private val PERMISSIONS = arrayOf(PermissionWithTextDescr.CAMERA)

    private val permissionDialog = PermissionDialog(onYesClick = { checkPermissionAndRequest() })

    private val requestPermissionLauncher = generateLauncherRequestPermission(PERMISSIONS.map {
        it.permission
    }.toTypedArray(),
        onAllGranted = {
            Toast.makeText(this@MainActivity, "GRANTED", Toast.LENGTH_SHORT).show()
        }, onUnGranted = { unGranted, neverAskAgain ->
            if (unGranted.isNotEmpty()) {
                Toast.makeText(this@MainActivity, "UN-GRANTED", Toast.LENGTH_SHORT).show()
                permissionDialog.show(supportFragmentManager, "permissionDialog")
            } else {
                Toast.makeText(this@MainActivity, "user don't let system request permission anymore", Toast.LENGTH_SHORT).show()
            }
        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            requestPermission()
        }
    }

    fun requestPermission(){

        if (!permissionDialog.isShowing) {
            checkPermissionAndRequest()
        }
    }


    private fun checkPermissionAndRequest() {
        requestPermissionLauncher?.launch(PERMISSIONS.map { it.permission }.toTypedArray())
    }

    fun generateLauncherRequestPermission(
        permissions: Array<String>, onAllGranted: (() -> Unit)? = null,
        onUnGranted: ((unGranted: Array<String>, neverAskAgain: Array<String>) -> Unit)? = null
    ): PermissionRequestLauncher<Array<String>>? {
        val launcher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
                val grantedPermissions = result.filter { it.value }
                if (grantedPermissions.size == permissions.size) {
                    onAllGranted?.invoke()
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        val unGrantedPermissions =
                            result.filter { !it.value && shouldShowRequestPermissionRationale(it.key) }
                        val neverAskAgainPermissions =
                            result.filter { !it.value && !shouldShowRequestPermissionRationale(it.key) }
                        onUnGranted?.invoke(
                            unGrantedPermissions.keys.toTypedArray(),
                            neverAskAgainPermissions.keys.toTypedArray()
                        )
                    } else {
                        val unGrantedPermissions =
                            result.filter { !it.value }
                        onUnGranted?.invoke(
                            unGrantedPermissions.keys.toTypedArray(),
                            arrayOf()
                        )
                    }
                }
            }
        return PermissionRequestLauncher<Array<String>>(launcher = launcher)
    }

}