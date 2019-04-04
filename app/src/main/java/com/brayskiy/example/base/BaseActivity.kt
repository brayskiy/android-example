package com.brayskiy.example.base

import android.content.Context
import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

import com.brayskiy.example.App
import com.brayskiy.example.R
import com.brayskiy.example.core.log.Log
import com.brayskiy.example.core.model.DialogData
import com.brayskiy.example.core.ui.ToastMessage
import com.brayskiy.example.dagger.ActivityComponent
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

/**
 * Created by brayskiy on 01/31/19.
 */

abstract class BaseActivity<A : IContract.Adapter, H : IContract.Handler<A>> : AppCompatActivity(), IContract.Adapter {
    abstract var layoutId: Int

    lateinit var activityComponent: ActivityComponent

    lateinit var handler : H @Inject set

    @JvmField
    var dialog: AlertDialog? = null

    @JvmField
    var snackbar: Snackbar? = null

    interface OnClickCallback {
        fun run()
    }

    abstract fun setupActivity(activityComponent: ActivityComponent, savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        activityComponent = (application as App).getActivityComponent()

        setupActivity(activityComponent, savedInstanceState)
        handler.onActivityCreated(this, savedInstanceState ?: Bundle())
    }

    protected fun initToolBar(toolbar: Toolbar, callback: BaseActivity.OnClickCallback?) {
        try {
            setSupportActionBar(toolbar)
            if (supportActionBar != null) {
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                supportActionBar!!.setDisplayShowHomeEnabled(true)
                supportActionBar!!.setDisplayShowTitleEnabled(true)
            }

            toolbar.setNavigationOnClickListener { view: View -> callback?.run() }
        } catch (e: Exception) {
            Log.e(e)
        }
    }

    override fun onStart() {
        super.onStart()
        @Suppress("UNCHECKED_CAST")
        handler.onAddViewAdapter(this as A, intent.extras)
        handler.onActivityStarted(this)
    }

    override fun onResume() {
        super.onResume()
        handler.onActivityResumed(this)
    }

    override fun onPause() {
        super.onPause()
        handler.onActivityPaused(this)
    }

    override fun onStop() {
        super.onStop()
        handler.onActivityStopped(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.onActivityDestroyed(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        handler.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        handler.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        handler.onActivitySaveInstanceState(this, outState)
    }

    // TODO
    protected fun initToolBar(toolbar: Toolbar, navigationIconId: Int, callback: BaseActivity.OnClickCallback?) {
        try {
            setSupportActionBar(toolbar)
            if (supportActionBar != null) {
                supportActionBar!!.setDisplayHomeAsUpEnabled(navigationIconId != 0)
                supportActionBar!!.setDisplayShowHomeEnabled(navigationIconId != 0)
                supportActionBar!!.setDisplayShowTitleEnabled(true)
            }

            if (navigationIconId != 0) {
                toolbar.setNavigationIcon(navigationIconId)
            }

            toolbar.setNavigationOnClickListener { view: View ->
                callback?.run()
            }
        } catch (e: Exception) {
            Log.e(e)
        }
    }

    override fun hideDialog() {
        dialog?.dismiss()
        dialog = null
        snackbar?.dismiss()
        snackbar = null
    }

    override fun displayDialog(dialogData: DialogData) {
        try {
            if (!isFinishing) {
                hideDialog()
                val message = DialogData.getMessage(this, dialogData)
                val title = DialogData.getTitle(this, dialogData)
                var positiveButtonText = DialogData.getPositiveButtonText(this, dialogData)
                val negativeButtonText = DialogData.getNegativeButtonText(this, dialogData)
                val contextThemeWrapper = ContextThemeWrapper(this, R.style.AppThemeAlertDialog)
                when (dialogData.type) {
                    DialogData.Type.ALERT -> {
                        if (positiveButtonText.isEmpty()) {
                            positiveButtonText = getString(R.string.ok)
                        }
                        val builder = AlertDialog.Builder(contextThemeWrapper)
                            .setCancelable(dialogData.isDismissible)
                            .setMessage(message)
                            .setPositiveButton(positiveButtonText) { _, _ -> dialogData.positiveCallback?.run() }
                            .setOnDismissListener { dialogData.dismissCallback?.run() }
                        if (title.isNotEmpty()) {
                            builder.setTitle(title)
                        }
                        if (negativeButtonText.isNotEmpty()) {
                            builder.setNegativeButton(negativeButtonText) { _, _ -> dialogData.negativeCallback?.run() }
                        }

                        if (dialogData.dialogView != null) {
                            builder.setView(dialogData.dialogView)
                        }

                        dialog = builder.create()
                        if (!this.isFinishing) {
                            dialog?.show()
                        }
                    }

                    DialogData.Type.ERROR -> {
                        if (positiveButtonText.isEmpty()) {
                            positiveButtonText = getString(R.string.ok)
                        }
                        val builder = AlertDialog.Builder(contextThemeWrapper)
                            .setCancelable(dialogData.isDismissible)
                            .setMessage(message)
                            .setPositiveButton(positiveButtonText) { _, _ -> dialogData.positiveCallback?.run() }
                        if (negativeButtonText.isNotEmpty()) {
                            builder.setNegativeButton(negativeButtonText) { _, _ -> dialogData.negativeCallback?.run() }
                        }
                        dialog = builder.create()
                        if (!this.isFinishing) {
                            dialog?.show()
                        }
                    }

                    DialogData.Type.PROGRESS -> {
                        if (!isFinishing) {
                            val progressView = LayoutInflater.from(this).inflate(R.layout.alert_progress,
                                null, false)
                            val messageView = progressView.findViewById<TextView>(R.id.alert_progress_text)
                            if (message.isNotEmpty()) {
                                messageView.text = message
                            }
                            val progressThemeWrapper = ContextThemeWrapper(this, R.style.AppThemeProgressAlertDialog)
                            val builder = AlertDialog.Builder(progressThemeWrapper)
                                .setCancelable(dialogData.isDismissible)
                                .setView(progressView)
                                .setOnDismissListener { dialogData.dismissCallback?.run() }
                            dialog = builder.create()
                            if (!this.isFinishing) {
                                dialog?.show()
                            }
                        }
                    }

                    DialogData.Type.TOAST -> {
                        if (!isFinishing) {
                            ToastMessage.show(this, message, R.drawable.toast_backgrpond, object : ToastMessage.Callback {
                                override fun run() {
                                    dialogData.positiveCallback?.run()
                                }
                            })
                        }
                    }

                    DialogData.Type.SNACKBAR -> {
                        val rootView = findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
                        val snackbar = Snackbar.make(rootView, message, dialogData.displayLength)
                        snackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_gray_blue))
                        val snackbarTextView = snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                        snackbarTextView.setTextColor(ContextCompat.getColor(this, R.color.white))
                        val snackbarActionButton = snackbar.view.findViewById<Button>(com.google.android.material.R.id.snackbar_action)
                        snackbarActionButton.setTextColor(ContextCompat.getColor(this, R.color.white))
                        this.snackbar = snackbar
                        if (positiveButtonText.isNotEmpty()) {
                            snackbar.setAction(positiveButtonText) { dialogData.positiveCallback?.run() }
                        }
                        if (!isFinishing) {
                            snackbar.show()
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.d(e.message)
        }
    }

    override fun showProgress(message: String?) {
        val dialogData = DialogData(DialogData.Type.PROGRESS)
        if (message != null) {
            dialogData.message = message
        }
        displayDialog(dialogData)
    }

    override fun hideProgress() {
        hideDialog()
    }

    override fun hideSoftKeyboard() {
        val focusView = currentFocus
        if (focusView != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(focusView.windowToken, 0)
        }
    }
}
