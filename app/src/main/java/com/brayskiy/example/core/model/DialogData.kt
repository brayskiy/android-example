package com.brayskiy.example.core.model

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

/**
 * Created by brayskiy on 01/31/19.
 */


data class DialogData @JvmOverloads constructor(var type: Type = Type.ALERT) {

    @StringRes
    var messageResId: Int = 0
    var message: String? = null
    @StringRes
    var titleResId: Int = 0
    var title: String? = null
    var isDismissible = true
    @StringRes
    var positiveButtonResId: Int = 0
    var positiveButtonText: String? = null
    @StringRes
    var negativeButtonResId: Int = 0
    var negativeButtonText: String? = null
    var positiveCallback: Runnable? = null
    var negativeCallback: Runnable? = null
    var dismissCallback: Runnable? = null
    var displayLength = 0
    var dialogView: View? = null

    enum class Type {
        ALERT,
        PROGRESS,
        ERROR,
        TOAST,
        SNACKBAR;
    }

    companion object {

        const val TOAST_LENGTH_SHORT = Toast.LENGTH_SHORT
        const val TOAST_LENGTH_LONG = Toast.LENGTH_LONG
        const val SNACK_BAR_LENGTH_SHORT = Snackbar.LENGTH_SHORT
        const val SNACK_BAR_LENGTH_LONG = Snackbar.LENGTH_LONG
        const val SNACK_BAR_LENGTH_INDEFINITE = Snackbar.LENGTH_INDEFINITE

        @JvmStatic
        fun getMessage(context: Context, dataDialog: DialogData): String {
            val message = if (dataDialog.messageResId > 0) {
                context.getString(dataDialog.messageResId)
            } else {
                dataDialog.message
            }
            return message ?: ""
        }

        @JvmStatic
        fun getTitle(context: Context, dataDialog: DialogData): String {
            val title = if (dataDialog.titleResId > 0) {
                context.getString(dataDialog.titleResId)
            } else {
                dataDialog.title
            }
            return title ?: ""
        }

        @JvmStatic
        fun getPositiveButtonText(context: Context, dataDialog: DialogData): String {
            val positiveText = if (dataDialog.positiveButtonResId > 0) {
                context.getString(dataDialog.positiveButtonResId)
            } else {
                dataDialog.positiveButtonText
            }
            return positiveText ?: ""
        }

        @JvmStatic
        fun getNegativeButtonText(context: Context, dataDialog: DialogData): String {
            val negativeText = if (dataDialog.negativeButtonResId > 0) {
                context.getString(dataDialog.negativeButtonResId)
            } else {
                dataDialog.negativeButtonText
            }
            return negativeText ?: ""
        }

        @JvmStatic
        fun createDialogData(type: Type, @StringRes messageId: Int = -1): DialogData {
            val dialogData = DialogData(type)
            if (messageId != -1) {
                dialogData.messageResId = messageId
            }
            return dialogData
        }

        @JvmStatic
        @JvmOverloads
        fun createLongToastInstance(@StringRes messageId: Int = -1): DialogData {
            val dialogData = DialogData(Type.TOAST)
            dialogData.displayLength = TOAST_LENGTH_LONG
            if (messageId != -1) {
                dialogData.messageResId = messageId
            }
            return dialogData
        }

        @JvmStatic
        @JvmOverloads
        fun createShortToastInstance(@StringRes messageId: Int = -1): DialogData {
            val dialogData = DialogData(Type.TOAST)
            dialogData.displayLength = TOAST_LENGTH_SHORT
            if (messageId != -1) {
                dialogData.messageResId = messageId
            }
            return dialogData
        }

        @JvmStatic
        @JvmOverloads
        fun createProgressDialogInstance(@StringRes messageId: Int = -1): DialogData {
            val dialogData = DialogData(Type.PROGRESS)
            if (messageId != -1) {
                dialogData.messageResId = messageId
            }
            return dialogData
        }

        @JvmStatic
        @JvmOverloads
        fun createErrorDialogInstance(@StringRes messageId: Int = -1): DialogData {
            val dialogData = DialogData(Type.ERROR)
            if (messageId != -1) {
                dialogData.messageResId = messageId
            }
            return dialogData
        }

        @JvmStatic
        @JvmOverloads
        fun createAlertDialogInstance(@StringRes messageId: Int = -1): DialogData {
            val dialogData = DialogData(Type.ALERT)
            if (messageId != -1) {
                dialogData.messageResId = messageId
            }
            return dialogData
        }

        @JvmStatic
        @JvmOverloads
        fun createLongSnackBarInstance(@StringRes messageId: Int = -1): DialogData {
            val dialogData = DialogData(Type.SNACKBAR)
            dialogData.displayLength = SNACK_BAR_LENGTH_LONG
            if (messageId != -1) {
                dialogData.messageResId = messageId
            }
            return dialogData
        }

        @JvmStatic
        @JvmOverloads
        fun createShortSnackBarInstance(@StringRes messageId: Int = -1): DialogData {
            val dialogData = DialogData(Type.SNACKBAR)
            dialogData.displayLength = SNACK_BAR_LENGTH_SHORT
            if (messageId != -1) {
                dialogData.messageResId = messageId
            }
            return dialogData
        }

        @JvmStatic
        @JvmOverloads
        fun createIndefiniteSnackBarInstance(@StringRes messageId: Int = -1): DialogData {
            val dialogData = DialogData(Type.SNACKBAR)
            dialogData.displayLength = SNACK_BAR_LENGTH_INDEFINITE
            if (messageId != -1) {
                dialogData.messageResId = messageId
            }
            return dialogData
        }

    }
}
