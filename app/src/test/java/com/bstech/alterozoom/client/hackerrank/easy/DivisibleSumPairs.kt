package com.brayskiy.example.hackerrank.easy

import org.junit.Test

import org.junit.Assert.*

class DivisibleSumPairs {
    @Test
    fun test_divisibleSumPairs() {
        run {
            val ar: Array<Int> = arrayOf(1, 3, 2, 6, 1, 2)
            val k = 3
            val expected = 5
            val actual = divisibleSumPairs(k, ar)

            assertEquals(expected, actual)
        }

        run {
            val ar: Array<Int> = arrayOf(27, 89, 4, 69, 18, 56, 93, 41, 51, 11, 39, 48, 99, 57, 67, 32, 23, 23, 39, 70, 26, 79,
                93, 75, 76, 72, 36, 88, 60, 67, 95, 58, 29, 7, 70, 60, 6, 72, 24, 97, 19, 98, 64, 38, 14, 64, 88, 34, 5, 98,
                8, 79, 57, 5, 43, 27, 57, 77, 89, 8, 45, 66, 60, 98, 20, 79, 99, 98, 6, 48, 42, 77, 43, 83, 48, 77, 83, 49, 40,
                32, 13, 99, 23, 55, 2, 94, 80, 62, 20, 60, 97, 80, 9, 54, 67, 84, 60, 62, 97, 64
            )
            val k = 95
            val expected = 36
            val actual = divisibleSumPairs(k, ar)

            assertEquals(expected, actual)
        }

        run {
            val ar: Array<Int> = arrayOf(6, 89, 79, 89, 22, 40, 61, 1, 76, 78, 66, 47, 17, 31, 6, 17, 36, 36, 66, 34, 64, 66,
                60, 45, 37, 67, 39, 48, 59, 56, 45, 74, 40, 6, 34, 8, 63, 19, 28, 81, 65, 23, 26, 24, 53, 29, 98, 55, 23, 74,
                99, 67, 80, 73, 86, 57, 32, 30, 40, 46, 20, 62, 7, 73, 58, 67, 15, 74, 63, 49, 42, 81, 38, 85, 47, 29, 88, 41,
                59, 19, 62, 64, 36, 46, 45, 3, 59, 91, 8, 60, 80, 58, 41, 2, 5, 27, 59, 92, 83, 79
            )
            val k = 86
            val expected = 70
            val actual = divisibleSumPairs(k, ar)

            assertEquals(expected, actual)
        }

        run {
            val ar: Array<Int> = arrayOf(1, 3)
            val k = 4
            val expected = 1
            val actual = divisibleSumPairs(k, ar)

            assertEquals(expected, actual)
        }

        run {
            val ar: Array<Int> = arrayOf(3)
            val k = 3
            val expected = 0
            val actual = divisibleSumPairs(k, ar)

            assertEquals(expected, actual)
        }

        run {
            val ar: Array<Int> = arrayOf(8, 10)
            val k = 2
            val expected = 1
            val actual = divisibleSumPairs(k, ar)

            assertEquals(expected, actual)
        }
    }

    fun divisibleSumPairs(k: Int, ar: Array<Int>): Int {
        var retVal = 0

        ar.sort()

        for (i in (ar.size - 1) downTo 1  ) {
            for (j in (i - 1) downTo 0 ) {
                val sum = ar[i] + ar[j]
                if (sum < k) {
                    break
                } else {
                    if (sum % k == 0) {
                        retVal++
                    }
                }
            }
        }

        return retVal
    }

}
