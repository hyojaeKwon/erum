/*
 * Copyright 2026 Hyojae Kwon
 * Licensed under the MIT License.
 */
package set

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class KoreanNameOptionsTest {

    @Test
    @DisplayName("기본 KoreanNameOptions 값은 유효하다")
    fun `default options are valid`() {
        val options = KoreanNameOptions()

        assertEquals(false, options.lastName)
        assertEquals(2, options.maxFirstNameLength)
    }

    @Test
    @DisplayName("maxFirstNameLength가 1 미만이면 예외를 던진다")
    fun `maxFirstNameLength below range throws`() {
        assertFailsWith<IllegalArgumentException> {
            KoreanNameOptions(maxFirstNameLength = 0)
        }
    }

    @Test
    @DisplayName("maxFirstNameLength가 3 초과면 예외를 던진다")
    fun `maxFirstNameLength above range throws`() {
        assertFailsWith<IllegalArgumentException> {
            KoreanNameOptions(maxFirstNameLength = 4)
        }
    }
}
