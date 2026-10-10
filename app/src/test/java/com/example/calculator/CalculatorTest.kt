package com.example.calculator

import org.junit.Assert.assertEquals
import org.junit.Test

class CalculatorTest {
    @Test
    fun add_twoPositiveNumbers_returnsSum() {
        assertEquals(5.0, Calculator.add(2.0, 3.0), 0.0)
    }

    @Test
    fun add_withNegativeNumber_returnsSum() {
        assertEquals(-1.0, Calculator.add(2.0, -3.0), 0.0)
    }

    @Test
    fun add_withZero_returnsSameNumber() {
        assertEquals(7.0, Calculator.add(7.0, 0.0), 0.0)
    }
}
