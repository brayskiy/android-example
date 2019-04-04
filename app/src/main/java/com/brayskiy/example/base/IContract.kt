package com.brayskiy.example.base

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.brayskiy.example.core.model.DialogData

/**
 * Created by brayskiy on 01/31/19.
 */

interface IContract {
    interface Handler<T: IContract.Adapter> : Application.ActivityLifecycleCallbacks {
        fun onAddViewAdapter(adapter: T, extras: Bundle?)
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
        fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    }

    interface Adapter {
        fun hideDialog()
        fun displayDialog(dialogData: DialogData)
        fun showProgress(message: String? = null)
        fun hideProgress()
        fun hideSoftKeyboard()
    }

    interface FragmentHandler<A : IContract.FragmentAdapter> {
        fun onFragmentSet(fragment: Fragment)
        fun onFragmentAdded(fragmentAdapter: A, extras: Bundle?, savedInstanceState: Bundle?)
        fun onFragmentRemoved()
        fun onFragmentPaused()
        fun onFragmentResumed()
        fun onSaveInstanceState(outState: Bundle)
    }

    interface FragmentAdapter {
        fun hideDialog()
        fun displayDialog(dialogData: DialogData)
        fun showProgress(message: String? = null)
        fun hideProgress()
        fun hideSoftKeyboard()
    }
}
