plugins {
    kotlin("jvm") version "2.3.0"
    id("com.diffplug.spotless") version "6.25.0"
}

group = "set"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }

}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
}

spotless {
    kotlin {
        target("**/*.kt")
        licenseHeader(
            """
            /*
             * Copyright ${'$'}YEAR Hyojae Kwon
             * Licensed under the MIT License.
             */
            """.trimIndent()
        )
    }
}