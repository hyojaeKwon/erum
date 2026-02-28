/*
 * Copyright 2026 Hyojae Kwon
 * Licensed under the MIT License.
 */
package set.internal.generator

import set.Config
import set.internal.RandomSource

internal interface GeneratorStrategy {
    fun generate(rng: RandomSource, config: Config): String
}