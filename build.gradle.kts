import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	val kotlinVersion = "1.9.22"
	val springVersion = "3.3.0-SNAPSHOT"

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
	implementation("org.flywaydb:flyway-core:7.10.0")
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
		property ("sonar.login", "admin")
		property ("sonar.password", "admin")
	}
}
