plugins {
    kotlin("jvm") version "2.0.20"
    application
    id("io.ktor.plugin") version "2.3.12"
}

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:32.0.0-android")
    implementation("org.slf4j:slf4j-simple:2.0.9")
    implementation("io.ktor:ktor-server-core:2.3.0")
    implementation("io.ktor:ktor-server-netty:2.3.0")
    implementation("io.ktor:ktor-serialization:2.3.0")
    testImplementation("io.ktor:ktor-server-tests:2.3.0")
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass.set("api.MainKt")
}