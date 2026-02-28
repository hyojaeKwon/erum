/*
 * Copyright 2026 Hyojae Kwon
 * Licensed under the MIT License.
 */
package set.internal.unique

internal class UniqueTracker {

    private val seen = HashSet<String>()

    fun isUnique(name: String): Boolean {
        return seen.add(name)
    }
}