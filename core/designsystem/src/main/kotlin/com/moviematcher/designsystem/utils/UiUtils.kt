package com.moviematcher.designsystem.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent

object UiUtils {

    /**
     * Copies the given text to the clipboard.
     *
     * @param context The application or activity context.
     * @param label An optional label for the clipboard entry. Defaults to "label".
     * @param text The text to be copied to the clipboard.
     */
    fun copyToClipBoard(
        context: Context,
        label: String = "label",
        text: String,
    ) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
    }

    /**
     * Shares the given text using available sharing apps on the device.
     *
     * @param context The application or activity context.
     * @param subject The subject of the shared content (optional).
     * @param text The text to be shared.
     */
    fun shareCode(context: Context, text: String, subject: String? = null) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
            subject?.let {
                putExtra(Intent.EXTRA_SUBJECT, it)
            }
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share via"))
    }
}