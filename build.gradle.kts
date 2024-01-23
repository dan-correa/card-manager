import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.3.0-SNAPSHOT"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
	id("org.sonarqube") version "4.4.1.3373"
}
val java_version = JavaVersion.VERSION_17

val kotlin = KotlinVersion.CURRENT

group = "com.pdi"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = java_version
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
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
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
