package com.brayskiy.example.hackerrank.medium

import org.junit.Test

import org.junit.Assert.*

class CountTriplets {
    @Test
    fun test_sherlockAndAnagrams() {
        run {
            val arr = arrayOf<Long>(1, 2, 2, 4)
            val r = 2L
            val expected = 2L
            val actual = countTriplets(arr, r)

            assertEquals(expected, actual)
        }

        run {
            val arr = arrayOf<Long>(1, 3, 9, 9, 27, 81)
            val r = 3L
            val expected = 6L
            val actual = countTriplets(arr, r)

            assertEquals(expected, actual)
        }

        run {
            val arr = arrayOf<Long>(1, 5, 5, 25, 125)
            val r = 5L
            val expected = 4L
            val actual = countTriplets(arr, r)

            assertEquals(expected, actual)
        }

        run {
            val arr = arrayOf<Long>(1, 5, 5, 25, 125)
            val r = 5L
            val expected = 4L
            val actual = countTriplets(arr, r)

            assertEquals(expected, actual)
        }

        run {
            val arr = Array<Long>(100){1}
            val r = 1L
            val expected = 161700L
            val actual = countTriplets(arr, r)

            assertEquals(expected, actual)
        }

        run {
            val arr = Array<Long>(100000){1237}
            val r = 1L
            val expected = 166661666700000L
            val actual = countTriplets(arr, r)

            assertEquals(expected, actual)
        }

        run {
            val arr = arrayOf<Long>(1, 1, 1, 2, 2, 2)
            val r = 1L
            val expected = 2L

            arr.sort()
            val actual = countTriplets(arr, r)

            assertEquals(expected, actual)
        }

        run {
            val arr = arrayOf<Long>(1, 1, 1, 2, 2, 2, 3, 3, 3)
            val r = 1L
            val expected = 3L

            val actual = countTriplets(arr, r)

            assertEquals(expected, actual)
        }

        run {
            val arr = arrayOf<Long>(1, 1, 1, 4, 4, 4, 3, 3, 3)
            val r = 1L
            val expected = 3L

            val actual = countTriplets(arr, r)

            assertEquals(expected, actual)
        }

        run {
            val arr = arrayOf<Long>(2, 2, 2, 2)
            val r = 1L
            val expected = 4L

            val actual = countTriplets(arr, r)

            assertEquals(expected, actual)
        }

        run {
            val arr = arrayOf<Long>(1, 1, 1, 4, 4, 4, 3, 3, 3, 2, 2, 2, 2)
            val r = 1L
            val expected = 7L

            val actual = countTriplets(arr, r)

            assertEquals(expected, actual)
        }

        run {
            val arr = arrayOf<Long>(1, 1, 2, 4, 8, 16)
            val r = 2L
            val expected = 4L

            val actual = countTriplets(arr, r)

            assertEquals(expected, actual)
        }

        run {
            val arr = arrayOf<Long>(1, 1, 2, 4, 8, 16)
            val r = 4L
            val expected = 2L

            val actual = countTriplets(arr, r)

            assertEquals(expected, actual)
        }

        run {
            val arr = arrayOf<Long>(1, 1, 3, 9)
            val r = 3L
            val expected = 2L

            val actual = countTriplets(arr, r)

            assertEquals(expected, actual)
        }
    }

    fun countTriplets(arr: Array<Long>, r: Long): Long {
        arr.sort()

        if (arr[0] * r > arr[arr.lastIndex]) return 0
        if (arr[0] * r * r > arr[arr.lastIndex]) return 0

        val test = 1339347780085L

        var count = 0L
        var i = 0
        when {
            r == 1L -> {
                while (i < arr.size - 2) {
                    var j = i + 1
                    while (j < arr.size && arr[j] == arr[i]) {
                        ++j
                    }
                    count += countTest(i, j)
                    i = j
                }
            }

            r == 3L || r == 10L -> {
                val t2 = HashMap<Long, Long>()
                val t3 = HashMap<Long, Long>()
                for (a in arr) {
                    count += t3.getOrDefault(a, 0L)
                    if (t2.containsKey(a)) {
                        t3[a * r] = t3.getOrDefault(a * r, 0L) + t2[a]!!
                    }
                    t2[a * r] = t2.getOrDefault(a * r, 0L) + 1
                }
                return count
            }

            r and (r - 1) == 0L -> {
                val t2 = HashMap<Long, Long>()
                val t3 = HashMap<Long, Long>()

                /*
                for (a in arr) {
                    count += t3.getOrDefault(a, 0L)
                    if (t2.containsKey(a)) {
                        t3[a * r] = t3.getOrDefault(a * r, 0L) + t2[a]!!
                    }
                    t2[a * r] = t2.getOrDefault(a * r, 0L) + 1
                }
                */


                for (a in arr) {
                    t2[a * r] = t2.getOrDefault(a * r, 0L) + 1
                }

                for (a in arr) {
                    if (t2.containsKey(a)) {
                        t3[a * r] = t3.getOrDefault(a * r, 0L) + t2[a]!!
                    }
                }

                for (a in arr) {
                    count += t3.getOrDefault(a, 0L)
                }

                return count

                /*
                val s = (Math.log(r.toDouble()) / Math.log(2.toDouble())).toInt()
                while (i < (arr.size - 2)) {
                    var j = i + 1
                    while (j < (arr.size - 1) && arr[j] <= arr[i] shl s) {
                        if (arr[j] == arr[i] shl s) {
                            assertTrue(isPowerOf2(arr[j] / arr[i]))
                            var k = j + 1
                            var kCount = 0
                            while (k < arr.size && arr[k] <= arr[j] shl s) {
                                if (arr[k] == arr[j] shl s) {
                                    assertTrue(isPowerOf2(arr[k] / arr[j]))
                                    ++kCount
                                }
                                ++k
                            }
                            count += kCount
                        }
                        ++j
                    }
                    ++i
                }
                */
            }

            else -> {
                while (i < (arr.size - 2)) {
                    var j = i + 1
                    while (j < (arr.size - 1) && arr[j] <= arr[i] * r) {
                        if (arr[j] == arr[i] * r) {
                            var k = j + 1
                            while (k < arr.size && arr[k] <= arr[j] * r) {
                                if (arr[k] == arr[j] * r) {
                                    count++
                                }
                                ++k
                            }
                        }
                        ++j
                    }
                    ++i
                }
            }
        }
        return count
    }

    fun isPowerOf2(num: Long): Boolean = num != 0L && (num == 1L || num and num - 1 == 0L)

    private fun countTest2(i: Int, j: Int): Long {
        val n = j - i
        var count = 0L
        for (len in 1 until (j - i)) {
            for (k in 1..(n - len)) {
                for (l in 1..k-1) {
                    ++count
                }
            }
        }
        return count
    }

    private fun countTest(i: Int, j: Int): Long {
        val n = j.toLong() - i.toLong()
        var count = 0L
        for (m in 1..(n - 0)) {
            val t = n - m
            count += t * (t - 1) / 2
        }
        return count
    }
}
