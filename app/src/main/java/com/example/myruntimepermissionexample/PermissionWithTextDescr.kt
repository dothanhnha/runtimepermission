package com.example.myruntimepermissionexample

import android.Manifest

enum class PermissionWithTextDescr(val permission: String, val textRequestPermission: String) {
    CAMERA(Manifest.permission.CAMERA, "Cannot open camera, please grant Camera permission");

    companion object {
        fun get(permission: String): PermissionWithTextDescr? =
            PermissionWithTextDescr.values().firstOrNull { it.permission == permission }
    }

}