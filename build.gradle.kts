import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.Exec


plugins {
    val kotlinVersion = "1.9.22"
    val springVersion = "3.2.2"

    id("org.springframework.boot") version springVersion
    id("io.spring.dependency-management") version "1.1.4"
    id("org.sonarqube") version "4.4.1.3373"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
}

val javaVersion = JavaVersion.VERSION_21

group = "com.pdi"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = javaVersion
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.flywaydb:flyway-core:9.22.3")
    runtimeOnly("org.postgresql:postgresql")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

sonar {
    properties {
        property("sonar.projectKey", "card-manager")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.language", "kotlin")
        property("sonar.login", "admin")
        property("sonar.password", "admin")
    }
}



tasks.register<Exec>("startPostgres") {

    val containerExists = {

        try {

            val output = StringBuilder()

            "docker ps --format \"{{.Names}}\"".runCommand(output)

            output.toString().contains("card-manager-db")

        } catch (e: Exception) {

            throw GradleException("Failed to check docker container: ${e.message}", e)

        }

    }

    val cmd = "docker run --name card-manager-db -p 5432:5433 -e POSTGRES_USER=card-manager-adm -e " +
            "POSTGRES_PASSWORD=card-manager-password -e POSTGRES_DB=card-manager-db -d postgres:16.1"

    if (!containerExists()) {

        println("Starting PostgreSQL Docker container...")

        // Splitting the command and executing it using the exec function

        val cmdParts = cmd.split("\\s".toRegex())

        val process = Runtime.getRuntime().exec(cmdParts.toTypedArray())

        // Wait for process to complete

        process.waitFor()

    } else {

        println("PostgreSQL Docker container already running.")

    }

}

tasks.named("startPostgres") {

    dependsOn("bootRun")

}

fun String.runCommand(output: StringBuilder) {

    val process = Runtime.getRuntime().exec(this)

    val reader = process.inputStream.bufferedReader()

    val lineSeparator = System.getProperty("line.separator")

    var line: String?

    while (reader.readLine().also { line = it } != null) {

        output.append(line).append(lineSeparator)

    }

    process.waitFor()

}