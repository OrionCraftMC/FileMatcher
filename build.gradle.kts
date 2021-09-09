plugins {
    kotlin("jvm") version "1.5.10"
    application
}

group = "io.github.orioncraftmc.tools"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.github.ajalt.clikt:clikt:3.2.0")
}

application {
    mainClass.set("io.github.orioncraftmc.tools.filematcher.MainKt")
}