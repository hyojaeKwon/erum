/*
 * Copyright 2026 Hyojae Kwon
 * Licensed under the MIT License.
 */
package set.internal.filterchain.filters

import set.Config

internal object BlockFilter : Filter {
    override fun accept(name: String, config: Config): Boolean {
        return config.blocklist.none { name.contains(it) }
    }
}