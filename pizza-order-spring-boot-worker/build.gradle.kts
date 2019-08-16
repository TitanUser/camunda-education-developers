import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val springBootAdminVersion: String by project

val env = mutableMapOf<String, Any>()
env.putAll(System.getenv())
env.putAll(extra.properties)

plugins {
    val kotlinVersion = "1.3.41"
    val springBootVersion = "2.1.7.RELEASE"
    val springDependencyMgr = "1.0.8.RELEASE"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version springDependencyMgr

    groovy
    `maven-publish`
}

dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation("org.camunda.bpm:camunda-external-task-client:1.2.0")
}

springBoot {
    buildInfo()
}

tasks.bootJar {
    manifest {
        attributes(
                "Implementation-Title" to rootProject.name,
                "Implementation-Version" to rootProject.version,
                "Built-By" to System.getProperty("user.name"),
                "Built-JDK" to System.getProperty("java.version"),
                "Build-Time" to LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
        )
    }
}