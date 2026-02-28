/*
 * Copyright 2026 Hyojae Kwon
 * Licensed under the MIT License.
 */
package internal

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import set.Config
import set.NameGenerator

class RandomSourceTest {

    @Test
    @DisplayName("서로 다른 seed는 일반적으로 다른 시퀀스를 생성한다")
    fun differentSeedUsuallyGeneratesDifferentSequence() {
        val a = NameGenerator(Config(seed = 1L)).batch(n = 30, allowDuplicate = true)
        val b = NameGenerator(Config(seed = 2L)).batch(n = 30, allowDuplicate = true)

        assertFalse(a == b)
    }
}
