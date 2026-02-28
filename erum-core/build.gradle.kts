plugins {
    kotlin("jvm")
}

group = (findProperty("GROUP") as String?) ?: "io.github.hyojaeKwon"
version = (findProperty("VERSION_NAME") as String?) ?: "0.1.0"

repositories {
    mavenCentral()
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