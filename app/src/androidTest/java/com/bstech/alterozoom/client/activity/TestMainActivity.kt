package com.bstech.alterozoom.client.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnitRunner
import androidx.test.runner.AndroidJUnit4
import com.brayskiy.example.R
import org.junit.Test
import org.junit.runner.RunWith
import com.brayskiy.example.activity.MainActivity
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class TestMainActivity: AndroidJUnitRunner() {

    @get:Rule
    var activityActivityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun testMainActivity() {
        onView(withId(R.id.home_tabs)).perform(click())
    }
}