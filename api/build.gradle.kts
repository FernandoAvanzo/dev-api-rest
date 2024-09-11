@file:Suppress("DEPRECATION")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.0.20"
    application
    id("io.ktor.plugin") version "2.3.12"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
}

version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("api.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:32.0.0-android")
    implementation("org.slf4j:slf4j-simple:2.0.9")
    implementation("io.ktor:ktor-server-core:2.3.12")
    implementation("io.ktor:ktor-server-netty:2.3.12")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.12")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.12")
    implementation("io.insert-koin:koin-ktor:3.5.6")
    implementation("io.insert-koin:koin-core:3.5.6")

    testImplementation(kotlin("test"))
    testImplementation("io.ktor:ktor-server-tests:2.3.12")
    testImplementation("io.ktor:ktor-server-test-host:2.3.12")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:2.0.20")
    testImplementation("io.mockk:mockk:1.13.4")
    testImplementation("io.ktor:ktor-client-content-negotiation:2.3.12")
    testImplementation("io.insert-koin:koin-test-junit5:3.5.6")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "21"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "21"
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "api.ApplicationKt"
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}