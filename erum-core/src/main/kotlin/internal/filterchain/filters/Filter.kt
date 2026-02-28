/*
 * Copyright 2026 Hyojae Kwon
 * Licensed under the MIT License.
 */
package set.internal.filterchain.filters

import set.Config

internal interface Filter {
    fun accept(name: String, config: Config): Boolean
}