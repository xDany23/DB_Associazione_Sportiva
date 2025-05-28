plugins {
    application
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
    mainClass = "db_lab.App"
}
