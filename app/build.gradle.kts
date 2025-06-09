plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.mysql:mysql-connector-j:9.3.0")
    testImplementation("org.assertj:assertj-core:3.27.3")
    testImplementation(libs.junit)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

application {
    mainClass = "db_ass.App"
}
