/*
 * Copyright 2026 Hyojae Kwon
 * Licensed under the MIT License.
 */
package set.internal

import set.Style
import set.internal.generator.EnglishWordStrategy
import set.internal.generator.GeneratorStrategy
import set.internal.generator.KoreanSyllableStrategy

internal object StyleRegistry {

    private val korean = KoreanSyllableStrategy()
    private val english = EnglishWordStrategy()

    fun get(style: Style): GeneratorStrategy {
        return when (style) {
            Style.KOREAN_GIVEN -> korean
//            Style.ENGLISH_NICKNAME -> english
        }
    }
}