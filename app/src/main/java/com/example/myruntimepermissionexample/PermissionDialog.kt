package com.example.myruntimepermissionexample

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class PermissionDialog(val onYesClick: (() -> Unit)? = null) :
    DialogFragment() {
    var isShowing = false

    override fun show(manager: FragmentManager, tag: String?) {
        if (!isShowing) {
            isShowing = true
            super.show(manager, tag)
        }
    }


    override fun showNow(manager: FragmentManager, tag: String?) {
        if (!isShowing) {
            isShowing = true
            super.showNow(manager, tag)
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it).apply {
                isCancelable = false
            }
            builder.setMessage("permission_request")
                .setPositiveButton("Yes",
                    DialogInterface.OnClickListener { dialog, id ->
                        onYesClick?.invoke()
                        dismiss()
                    }).setNegativeButton("Cancel") { dialog, id ->
                    dismiss()
                }
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        isShowing = false
    }
}