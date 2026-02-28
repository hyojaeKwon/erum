/*
 * Copyright 2026 Hyojae Kwon
 * Licensed under the MIT License.
 */
package set

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    println("=== namegen sample ===")

    // 1) 한국 이름: 이름만, 길이 최대 3, seed 고정
    val koreanGivenOnly = NameGenerator(
        Config(
            seed = 42L, koreanOptions = KoreanNameOptions(
                lastName = false, maxFirstNameLength = 3
            )
        )
    )

    println("\n-- Korean: seed=42, batch=10 --")
    koreanGivenOnly.batch(n = 10, allowDuplicate = false).forEach { println(it) }

    // 2) 한국 이름: 성+이름, 길이 최대 3, seed 고정, unique
    val koreanFullUnique = NameGenerator(
        Config(
            seed = 42L,
            koreanOptions = KoreanNameOptions(
                lastName = true, maxFirstNameLength = 3
            ),
        )
    )

    println("\n-- Korean: seed=42, unique=10 --")
    koreanFullUnique.batch(n = 10, allowDuplicate = false).forEach { println(it) }

    // 3) 성 이 주어졌을 때
    val koreanWithGivenLastName = NameGenerator(
        Config(
            seed = 42L,
            koreanOptions = KoreanNameOptions(
                lastName = true, maxFirstNameLength = 3
            )
        )
    )

    println("\n-- Korean: seed=42 --")
    print(koreanWithGivenLastName.next("김"))

    // 4) seed 없을 때(매번 다름)
    val noSeed = NameGenerator(
        Config(
            koreanOptions = KoreanNameOptions(lastName = true, maxFirstNameLength = 2)
        )
    )

    println("\n-- Korean (no seed), batch=10 --")
    noSeed.batch(n=10, allowDuplicate = false).forEach { println(it) }

    println("\nDone.")
}