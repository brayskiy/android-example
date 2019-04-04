package com.brayskiy.example.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.brayskiy.example.R
import com.brayskiy.example.base.BaseActivity
import com.brayskiy.example.contract.MainActivityContract
import com.brayskiy.example.core.log.Log
import com.brayskiy.example.core.model.DialogData
import com.brayskiy.example.dagger.ActivityComponent
import com.brayskiy.example.fragment.PopularMoviesFragment
import com.brayskiy.example.fragment.TopRatedMoviesFragment
import com.google.android.material.tabs.TabLayout
import java.util.*

/**
 * Created by brayskiy on 01/31/19.
 */

class MainActivity : BaseActivity<MainActivityContract.IAdapter, MainActivityContract.IHandler>(), MainActivityContract.IAdapter {
    override var layoutId = R.layout.activity_main

    internal var popularMoviesFragment = PopularMoviesFragment.newInstance()
    internal var topRatedMoviesFragment = TopRatedMoviesFragment.newInstance()

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    private val tabIconsMap = mutableMapOf<Int, Drawable>()

    inner class HomeFragmentPagerAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                HomeTabs.CONTROL_TAB -> popularMoviesFragment
                else -> topRatedMoviesFragment
            }
        }

        override fun getCount(): Int {
            return HomeTabs.COUNT
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                HomeTabs.CONTROL_TAB -> getString(R.string.home_tab_popular_movies)
                HomeTabs.SSH_TAB -> getString(R.string.home_tab_top_rated_movies)
                else -> return null
            }
        }
    }

    override fun setupActivity(activityComponent: ActivityComponent, savedInstanceState: Bundle?) {
        activityComponent.inject(this)

        viewPager = findViewById(R.id.home_viewpager)
        tabLayout = findViewById(R.id.home_tabs)
        //tabLayout.setupWithViewPager(viewPager)

        val adapter = HomeFragmentPagerAdapter(supportFragmentManager)

        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = HomeTabs.COUNT
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        val position = savedInstanceState?.getInt(CURRENT_TAB) ?: 0
        setupTabLayout(position)

        handler.onAddViewAdapter(this, savedInstanceState)

        handler.getActivityTitle()

        if (checkPermissions(REQUEST_PERMISSIONS, REQUIRED_PERMISSIONS)) {
            Log.i("Have to grant permissions")
        }
    }

    private fun checkPermissions(requestCode: Int, requiredPermissions: Array<String>): Boolean {
        val permissionsList = ArrayList<String>()

        for (permission in requiredPermissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                Log.d("Permission $permission not granted")
                permissionsList.add(permission)
            }
        }

        if (permissionsList.size > 0) {
            Log.i("Request to grant permissions: " + permissionsList.size)

            ActivityCompat.requestPermissions(this,
                permissionsList.toTypedArray(),
                requestCode)

            return false
        }

        return true
    }


    private fun setupTabLayout(position: Int) {
        val currentPosition = viewPager.currentItem
        tabLayout.removeAllTabs()

        for (index in 0 until HomeTabs.COUNT) {
            tabLayout.addTab(tabLayout.newTab().setIcon(
                getTabIcon(index, index == currentPosition)))
        }

        tabLayout.getTabAt(position)?.select()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.icon = getTabIcon(tab.position, true)
                viewPager.setCurrentItem(tab.position, true)
                hideSoftKeyboard()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.icon = getTabIcon(tab.position)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
    }

    private fun getTabIcon(position: Int, selected: Boolean = false): Drawable? {
        val icon: Int = when (position) {
            HomeTabs.CONTROL_TAB -> R.drawable.ic_baseline_touch_app_24px
            HomeTabs.SSH_TAB -> R.drawable.ic_baseline_network_check_24px
            else -> -1
        }

        if (icon == -1) return null

        val color = if (selected) R.color.tab_selected else R.color.tab_unselected
        val drawable: Drawable? = tabIconsMap[icon]
        val iconDrawable = if (drawable != null) {
            drawable.setColorFilter(
                ContextCompat.getColor(this, color), PorterDuff.Mode.SRC_IN)
            drawable
        } else {
            val newDrawable = ContextCompat.getDrawable(this, icon) as Drawable
            val newDrawableWrapper = DrawableCompat.wrap(newDrawable).mutate()
            newDrawableWrapper.setColorFilter(
                ContextCompat.getColor(this, color), PorterDuff.Mode.SRC_IN)
            newDrawableWrapper
        }

        tabIconsMap[icon] = iconDrawable

        return iconDrawable
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)

        for (i in 0 until menu.size()) {
            val drawable = menu.getItem(i).icon
            if (drawable != null) {
                drawable.mutate()
                drawable.setColorFilter(resources.getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
            }
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.home_menu_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }

            R.id.menu_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }

            R.id.menu_account -> {
                val dialogData = DialogData(DialogData.Type.ALERT)
                dialogData.titleResId = R.string.not_implemented_title
                dialogData.messageResId =  R.string.not_implemented_message
                displayDialog(dialogData)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setActivityTitle(title: String) {
        setTitle(title)
    }

    companion object {

        const val REQUEST_PERMISSIONS = 1101

        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE)

        private const val CURRENT_TAB = "current_tab"
    }
}
