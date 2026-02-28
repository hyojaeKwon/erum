/*
 * Copyright 2026 Hyojae Kwon
 * Licensed under the MIT License.
 */
package set

data class Config(
    val seed: Long? = null,
    val maxAttemptsPerName: Int = 100,
    val filtersEnabled: Boolean = false,
    val blocklist: Set<String> = emptySet(),
    val koreanOptions: KoreanNameOptions = KoreanNameOptions(),
)
