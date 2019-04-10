package com.brayskiy.example

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.ArgumentCaptor
import org.mockito.Captor

/**
 * Created by brayskiy on 04/05/19.
 */

class AnnotationsTest {

    @Mock lateinit var mockedList: ArrayList<String>

    @Captor lateinit var argCaptor: ArgumentCaptor<String>

    @Spy var spiedList: MutableList<String> = ArrayList()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun test_001_mocks() {
        mockedList.add("one")

        Mockito.verify(mockedList).add("one")
        assertEquals(0, mockedList.size)

        Mockito.`when`(mockedList.size).thenReturn(100)
        assertNotEquals(101, mockedList.size)
        assertEquals(100, mockedList.size)
    }

    @Test
    fun test_002_spys() {
        spiedList.add("one")
        spiedList.add("two")

        Mockito.verify<MutableList<String>>(spiedList).add("one")
        Mockito.verify<MutableList<String>>(spiedList).add("two")

        assertEquals(2, spiedList.size)

        Mockito.doReturn(100).`when`<MutableList<String>>(spiedList).size
        assertEquals(100, spiedList.size)
    }

    @Test
    fun test_003_captor() {
        mockedList.add("two")

        Mockito.verify(mockedList).add(argCaptor.capture())

        assertEquals("two", argCaptor.value)
    }
}
