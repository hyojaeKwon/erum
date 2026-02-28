/*
 * Copyright 2026 Hyojae Kwon
 * Licensed under the MIT License.
 */
package set

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UniqueTest {

    @Test
    @DisplayName("unique를 반복 호출하면 결과는 모두 유니크하다")
    fun uniqueNameTest() {
        val g = NameGenerator(Config(seed = 99L))
        val names = mutableListOf<String>()

        for (i in 0 until 100) {
            names.add(g.unique())
        }

        assertEquals(names.size, names.toSet().size)
    }
}
