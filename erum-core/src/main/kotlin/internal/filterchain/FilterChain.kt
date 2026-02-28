/*
 * Copyright 2026 Hyojae Kwon
 * Licensed under the MIT License.
 */
package set.internal.filterchain

import set.Config
import set.internal.filterchain.filters.BlockFilter
import set.internal.filterchain.filters.Filter

internal class FilterChain(
    private val config: Config,
    private val filters: List<Filter> = listOf(
        BlockFilter,
    ),
) {

    fun accept(name: String): Boolean {
        filters.forEach {
            if (!it.accept(name, config)) return false
        }
        return true
    }
}
