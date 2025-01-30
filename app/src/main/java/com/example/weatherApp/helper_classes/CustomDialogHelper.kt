package com.example.weatherApp.helper_classes

import android.app.AlertDialog
import android.content.Context
import com.example.weatherApp.R

object CustomDialogHelper {
    fun showRetryDialog(context: Context, onRetry: () -> Unit) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.request_failed))
            .setMessage(context.getString(R.string.try_again_message))
            .setPositiveButton(context.getString(R.string.retry)) { dialog, _ ->
                dialog.dismiss()
                onRetry() // קריאה לפעולה מחדש
            }
            .setNegativeButton(context.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
