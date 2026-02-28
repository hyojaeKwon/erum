/*
 * Copyright 2026 Hyojae Kwon
 * Licensed under the MIT License.
 */
package set

import set.internal.RandomSource
import set.internal.StyleRegistry
import set.internal.data.KoreanLastNames
import set.internal.data.KoreanSyllables
import set.internal.filterchain.FilterChain
import set.internal.unique.UniqueTracker

private data class KoreanNameParts(
    val lastName: String,
    val firstName: String,
) {
    val fullName: String
        get() = lastName + firstName
}

class NameGenerator(
    private val config: Config = Config(),
) {

    private val rng = RandomSource(config.seed)
    private val filterChain = FilterChain(config)
    private val tracker = UniqueTracker()

    fun next(): String {
        val parts = generateKoreanParts(
            enforceUnique = false,
            includeLastName = config.koreanOptions.lastName,
        )
        return parts.fullName
    }

    fun next(lastName: String): String {
        validateLastName(lastName)
        val parts = generateKoreanParts(
            enforceUnique = false,
            includeLastName = true,
            fixedLastName = lastName
        )
        return parts.fullName
    }

    fun batch(n: Int, allowDuplicate : Boolean): List<String> {
        val out = ArrayList<String>(n)
        if (allowDuplicate) {
            repeat(n) { out.add(next()) }
            return out
        }
        repeat(n) { out.add(unique()) }
        return out
    }

    fun unique(): String {
        val parts = generateKoreanParts(
            enforceUnique = true,
            includeLastName = config.koreanOptions.lastName,
        )
        return parts.fullName
    }

    private fun generateKoreanParts(
        enforceUnique: Boolean,
        includeLastName: Boolean,
        firstNameLength: Int? = null,
        fixedLastName: String? = null,
    ): KoreanNameParts {


        if (firstNameLength != null && (firstNameLength <= 0 || firstNameLength > config.koreanOptions.maxFirstNameLength)) {
            throw IllegalArgumentException("firstNameLength must be between 1 and ${config.koreanOptions.maxFirstNameLength}")
        }

        val strategy = StyleRegistry.get(Style.KOREAN_GIVEN)

        repeat(config.maxAttemptsPerName) {
            val firstName = if (firstNameLength == null) {
                strategy.generate(rng, config)
            } else {
                buildString {
                    repeat(firstNameLength) {
                        append(rng.pick(KoreanSyllables.pool))
                    }
                }
            }

            val selectedLastName = when {
                !includeLastName -> ""
                fixedLastName != null -> fixedLastName
                else -> rng.pick(KoreanLastNames.pool)
            }

            val parts = KoreanNameParts(lastName = selectedLastName, firstName = firstName)
            val candidate = parts.fullName

            if (config.filtersEnabled && !filterChain.accept(firstName)) return@repeat
            if (enforceUnique && !tracker.isUnique(candidate)) return@repeat

            return parts
        }

        throw IllegalStateException("Failed to generate name after ${config.maxAttemptsPerName} attempts.")
    }

    private fun validateLastName(lastName: String) {
        if (lastName.isEmpty()) {
            throw IllegalArgumentException("Given last name must not be empty")
        }

        if (lastName.length > 2) {
            throw IllegalArgumentException("Given last name must not be more than 2 characters")
        }
    }
}
