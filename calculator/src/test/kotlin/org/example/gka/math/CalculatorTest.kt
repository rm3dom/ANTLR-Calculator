package org.example.gka.math

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class CalculatorTest {
    @Test
    fun testCalc() {
        assertEquals(BigDecimal("2.664"), calc("1.2 * 2.22"))
        assertEquals(BigDecimal("5"), calc("1 + 2 * 2"))
        assertEquals(BigDecimal("6"), calc("(1 + 2) * 2"))
        assertEquals(BigDecimal("1.5"), calc("(1 + 2) / 2"))
    }
}