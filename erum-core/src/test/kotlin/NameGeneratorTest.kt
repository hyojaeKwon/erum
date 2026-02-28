/*
 * Copyright 2026 Hyojae Kwon
 * Licensed under the MIT License.
 */
package set

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class NameGeneratorTest {

    @Test
    @DisplayName("next는 비어있지 않은 이름을 반환한다")
    fun `next returns non-empty name`() {
        val generator = NameGenerator(Config(seed = 42L))

        val name = generator.next()

        assertTrue(name.isNotEmpty())
    }

    @Test
    @DisplayName("동일한 seed는 동일한 next 시퀀스를 생성한다")
    fun `same seed produces same next sequence`() {
        val config = Config(seed = 42L)
        val generatorA = NameGenerator(config)
        val generatorB = NameGenerator(config)

        val a = List(40) { generatorA.next() }
        val b = List(40) { generatorB.next() }

        assertEquals(a, b)
    }

    @Test
    @DisplayName("고정 성을 주면 해당 성으로 시작하는 이름을 반환한다")
    fun `next with fixed last name prefixes that last name`() {
        val generator = NameGenerator(Config(seed = 1L))

        val name = generator.next("김")

        assertTrue(name.startsWith("김"))
        assertTrue(name.length >= 2)
    }

    @Test
    @DisplayName("빈 성을 주면 예외를 던진다")
    fun `next with empty last name throws`() {
        val generator = NameGenerator(Config(seed = 1L))

        assertFailsWith<IllegalArgumentException> {
            generator.next("")
        }
    }

    @Test
    @DisplayName("성 길이가 2자를 초과하면 예외를 던진다")
    fun `next with last name longer than 2 throws`() {
        val generator = NameGenerator(Config(seed = 1L))

        assertFailsWith<IllegalArgumentException> {
            generator.next("김민수")
        }
    }

    @Test
    @DisplayName("중복 허용 배치는 요청한 개수를 반환한다")
    fun `batch returns requested size with duplicates allowed`() {
        val generator = NameGenerator(Config(seed = 7L))

        val names = generator.batch(n = 60, allowDuplicate = true)

        assertEquals(60, names.size)
    }

    @Test
    @DisplayName("중복 미허용 배치는 모두 유니크한 이름을 반환한다")
    fun `batch without duplicates returns unique names`() {
        val generator = NameGenerator(Config(seed = 9L))

        val names = generator.batch(n = 100, allowDuplicate = false)

        assertEquals(names.size, names.toSet().size)
    }

    @Test
    @DisplayName("unique는 중복되지 않는 이름을 생성한다")
    fun `unique generates non-duplicated names`() {
        val generator = NameGenerator(Config(seed = 11L))

        val names = List(120) { generator.unique() }

        assertEquals(names.size, names.toSet().size)
    }

    @Test
    @DisplayName("lastName=false, maxFirstNameLength=1이면 생성 이름 길이는 1자다")
    fun `korean option lastName false keeps generated name at first-name max length`() {
        val generator = NameGenerator(
            Config(
                seed = 3L,
                koreanOptions = KoreanNameOptions(lastName = false, maxFirstNameLength = 1),
            )
        )

        val names = List(40) { generator.next() }

        assertTrue(names.all { it.length == 1 })
    }

    @Test
    @DisplayName("lastName=true, maxFirstNameLength=1이면 전체 이름 길이는 2자다")
    fun `korean option lastName true increases total name length by family name`() {
        val generator = NameGenerator(
            Config(
                seed = 3L,
                koreanOptions = KoreanNameOptions(lastName = true, maxFirstNameLength = 1),
            )
        )

        val names = List(40) { generator.next() }

        assertTrue(names.all { it.length == 2 })
    }

    @Test
    @DisplayName("필터와 전체 음절 blocklist를 켜면 최대 시도 후 실패한다")
    fun `filter enabled with full blocklist fails after max attempts`() {
        val generator = NameGenerator(
            Config(
                seed = 1L,
                maxAttemptsPerName = 5,
                filtersEnabled = true,
                blocklist = setOf("하", "서", "윤", "지", "민", "아", "린", "우", "연", "준", "다", "라", "나"),
                koreanOptions = KoreanNameOptions(lastName = false, maxFirstNameLength = 2),
            )
        )

        assertFailsWith<IllegalStateException> {
            generator.next()
        }
    }

    @Test
    @DisplayName("필터와 특정 음절 blocklist를 켜면 해당 음절이 포함되지 않는다")
    fun `filter enabled with targeted blocklist rejects matching syllable`() {
        val generator = NameGenerator(
            Config(
                seed = 42L,
                filtersEnabled = true,
                blocklist = setOf("하"),
                koreanOptions = KoreanNameOptions(lastName = false, maxFirstNameLength = 2),
            )
        )

        val names = List(80) { generator.next() }

        assertFalse(names.any { it.contains("하") })
    }
}
