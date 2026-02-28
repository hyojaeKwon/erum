/*
 * Copyright 2026 Hyojae Kwon
 * Licensed under the MIT License.
 */
package set.internal.generator

import set.Config
import set.internal.RandomSource
import set.internal.data.EnglishWords

internal class EnglishWordStrategy : GeneratorStrategy {
    override fun generate(rng: RandomSource, config: Config): String {
        val adj = rng.pick(list = EnglishWords.adjectives)
        val noun = rng.pick(list = EnglishWords.nouns)
        return adj + noun
    }
}