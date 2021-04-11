package com.github.ronit.weather.utility

import android.Manifest
import android.app.Activity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

object PermissionManager {

    val permissionLocation = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    fun locationPermission(activity: Activity, listener: PermissionCallback) {
        Dexter.withContext(activity)
            .withPermissions(permissionLocation)
            .withListener(object: MultiplePermissionsListener {
                override fun onPermissionsChecked(permissionsReport: MultiplePermissionsReport?) {
                    permissionsReport?.run {
                        if(this.areAllPermissionsGranted()){
                            listener.permissionGranted(permissionLocation)
                        } else {
                            listener.permissionDecline(permissionLocation)
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(permissionRequestList: MutableList<PermissionRequest>?, permissionToken: PermissionToken?) {
                    permissionToken?.continuePermissionRequest()
                }

            })
            .onSameThread()
            .check()
    }

}


interface PermissionCallback {
    fun permissionGranted(whichPermission: List<String>)
    fun permissionDecline(whichPermission: List<String>)
}