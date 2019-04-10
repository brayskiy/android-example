package com.brayskiy.example

import android.content.SharedPreferences
import com.brayskiy.example.models.MovieSummary
import org.junit.Assert.assertEquals

import org.junit.Before
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import org.mockito.Matchers.*
import org.mockito.Mockito.*

/**
 * Created by brayskiy on 04/05/19.
 */

class AppModuleTest {
    @Mock lateinit var sharedPreferences: SharedPreferences
    @Mock lateinit var editor: SharedPreferences.Editor

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        `when`(sharedPreferences.getString(anyString(), anyString())).thenReturn(null)
        `when`(sharedPreferences.getInt(anyString(), anyInt())).thenReturn(0)

        `when`<SharedPreferences.Editor>(sharedPreferences.edit()).thenReturn(editor)
        `when`<SharedPreferences.Editor>(editor.putLong(anyString(), anyLong())).thenReturn(editor)
        `when`<SharedPreferences.Editor>(editor.putString(anyString(), anyString())).thenReturn(editor)
        `when`<SharedPreferences.Editor>(editor.putInt(anyString(), anyInt())).thenReturn(editor)
    }

    @Test
    fun test_001_sharedPreferences() {
        sharedPreferences.edit()
        verify<SharedPreferences>(sharedPreferences).edit()

        editor.putLong(anyString(), anyLong())
        verify<SharedPreferences.Editor>(editor).putLong(anyString(), anyLong())

        editor.putString(anyString(), anyString())
        verify<SharedPreferences.Editor>(editor).putString(anyString(), anyString())

        editor.apply()
        verify<SharedPreferences.Editor>(editor).apply()
    }
}
