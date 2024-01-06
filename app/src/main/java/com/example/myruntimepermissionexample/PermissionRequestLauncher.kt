package com.example.myruntimepermissionexample

import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityOptionsCompat

class PermissionRequestLauncher<I>(var launcher: ActivityResultLauncher<I>) {

       /* var isLaunching = false*/

        fun launch(input: I) {
            /*isLaunching = true*/
            launcher.launch(input)
        }

        fun launch(input: I, options: ActivityOptionsCompat?) {
            /*isLaunching = true*/
            launcher.launch(input, options)
        }

        fun unregister() {
           /* isLaunching = false*/
            launcher.unregister()
        }
    }