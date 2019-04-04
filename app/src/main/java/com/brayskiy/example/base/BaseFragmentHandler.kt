package com.brayskiy.example.base

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * Created by brayskiy on 01/31/19.
 */

open class BaseFragmentHandler<A : IContract.FragmentAdapter> : IContract.FragmentHandler<A>, BaseActionHandler() {

    @JvmField
    var fragment: Fragment? = null

    @JvmField
    var fragmentAdapter: A? = null

    override fun onFragmentSet(fragment: Fragment) {
        this.fragment = fragment
    }

    override fun onFragmentAdded(fragmentAdapter: A, extras: Bundle?, savedInstanceState: Bundle?) {
        this.fragmentAdapter = fragmentAdapter
        this.baseViewAdapter = fragmentAdapter
    }

    override fun onFragmentRemoved() {
        this.fragmentAdapter = null
        this.fragment = null
        this.baseViewAdapter = null
    }

    override fun onFragmentPaused() {

    }

    override fun onFragmentResumed() {

    }

    override fun onSaveInstanceState(outState: Bundle) {

    }

}
