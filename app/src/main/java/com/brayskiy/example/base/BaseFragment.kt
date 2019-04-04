package com.brayskiy.example.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brayskiy.example.core.model.DialogData
import com.brayskiy.example.dagger.ActivityComponent
import javax.inject.Inject

/**
 * Created by brayskiy on 01/31/19.
 */

abstract class BaseFragment<A : IContract.FragmentAdapter, T : IContract.FragmentHandler<A>> : Fragment(),
        IContract.FragmentAdapter {

    lateinit var handler: T @Inject set

    @get:LayoutRes
    abstract val layoutId: Int

    private lateinit var activityComponent: ActivityComponent

    protected abstract fun setupFragmentView(view: View)

    protected abstract fun injectFragment(injector: ActivityComponent)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(layoutId, container, false)
        setHasOptionsMenu(true)
        setupFragmentView(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO
        //helpView = (activity as BaseActivity<*, *>).helpView

        activityComponent = (activity as BaseActivity<*, *>).activityComponent
        injectFragment(activityComponent)
        handler.onFragmentSet(this)
        @Suppress("UNCHECKED_CAST")
        handler.onFragmentAdded(this as A, arguments, savedInstanceState)
    }

    override fun onDetach() {
        super.onDetach()
        handler.onFragmentRemoved()
    }

    override fun onResume() {
        super.onResume()
        handler.onFragmentResumed()
    }

    override fun onPause() {
        super.onPause()
        handler.onFragmentPaused()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        handler.onSaveInstanceState(outState)
    }

    override fun displayDialog(dialogData: DialogData) {
        (activity as BaseActivity<*, *>).displayDialog(dialogData)
    }

    override fun hideDialog() {
        (activity as BaseActivity<*, *>).hideDialog()
    }

    override fun showProgress(message: String?) {
        (activity as BaseActivity<*, *>).showProgress(message)
    }

    override fun hideProgress() {
        hideDialog()
    }

    override fun hideSoftKeyboard() {
        (activity as BaseActivity<*, *>).hideSoftKeyboard()
    }
}
