plugins {
    kotlin("jvm")
    application
}

group = "set"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("erum-sample/src/main/kotlin/Main.kt")
}

dependencies {
    implementation(project(":erum-core"))
}

kotlin {
    jvmToolchain(17)
}