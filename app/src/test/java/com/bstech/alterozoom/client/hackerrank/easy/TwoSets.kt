package com.brayskiy.example.hackerrank.easy

import org.junit.Test

import org.junit.Assert.*

class TwoSets {
    @Test
    fun test_twoSets() {
        val a: Array<Int> = arrayOf(3, 4)
        val b: Array<Int> = arrayOf(24, 48)

        val result = getTotalX(a, b)

        assertEquals(3, result)
    }

    fun getTotalX(a: Array<Int>, b: Array<Int>): Int {

        var multiple = 0
        for (i in b) {
            multiple = gcd(multiple, i)
        }

        println("Multiple: $multiple")

        var factor = 1
        for (i in a) {
            factor = lcm(factor, i)
            if (factor > multiple) {
                return 0
            }
        }

        if (multiple % factor != 0) {
            return 0;
        }

        println("Factor: $factor")

        val value = multiple / factor

        val max = Math.max(factor, value)
        var totalX = 1

        for (i in factor until multiple) {
            if (multiple % i == 0 && i % factor == 0) {
                totalX++
            }
        }

        return totalX
    }

    private fun gcd(a: Int, b: Int): Int {
        var aLoc = a
        var bLoc = b
        while (aLoc > 0 && bLoc > 0) {

            if (aLoc >= bLoc) {
                aLoc %= bLoc
            } else {
                bLoc %= aLoc
            }
        }

        return aLoc + bLoc
    }

    private fun lcm(a: Int, b: Int): Int = a / gcd(a, b) * b

}
