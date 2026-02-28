/*
 * Copyright 2026 Hyojae Kwon
 * Licensed under the MIT License.
 */
package set

data class KoreanNameOptions(
    val lastName: Boolean = false,
    val maxFirstNameLength: Int = 2,
) {
    init {
        require(maxFirstNameLength in 1..3) {
            "maxFirstNameLength must be 1, 2, or 3"
        }
    }
}
