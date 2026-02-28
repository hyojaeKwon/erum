/*
 * Copyright 2026 Hyojae Kwon
 * Licensed under the MIT License.
 */
package set.internal

import kotlin.random.Random

internal class RandomSource(
    seed: Long? = null,
) {
    private val random = seed?.let { Random(it) } ?: Random.Default

    fun nextInt(bound: Int): Int {
        return random.nextInt(bound)
    }

    fun <T> pick(list: List<T>): T {
        return list[nextInt(list.size)]
    }
}