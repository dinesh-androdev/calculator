package com.example.calculator

/**
 * Pure calculation logic, kept separate from the UI so it is easy to unit test.
 * Add one operation at a time here as you expand the app.
 */
object Calculator {
    fun add(
        a: Double,
        b: Double,
    ): Double = b + a
}
