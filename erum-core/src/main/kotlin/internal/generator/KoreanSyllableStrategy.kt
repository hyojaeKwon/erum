/*
 * Copyright 2026 Hyojae Kwon
 * Licensed under the MIT License.
 */
package set.internal.generator

import set.Config
import set.internal.RandomSource
import set.internal.data.KoreanSyllables

internal class KoreanSyllableStrategy : GeneratorStrategy {
    override fun generate(rng: RandomSource, config: Config): String {
        val firstNameSize = firstNameSize(rng)
        val nameLength = if (firstNameSize > config.koreanOptions.maxFirstNameLength) {
            config.koreanOptions.maxFirstNameLength
        }else{
            firstNameSize
        }

        return buildString {
            repeat(nameLength) {
                append(rng.pick(KoreanSyllables.pool))
            }
        }
    }

    fun firstNameSize(rng: RandomSource): Int {
        val value = rng.nextInt(100)

        if (value < 5) {
            return 1
        }
        if (value < 99) {
            return 2
        }
        return 3
    }

    private fun IntRange.random(rng: RandomSource): Int {
        val size = last - first + 1
        return first + rng.nextInt(size)
    }
}