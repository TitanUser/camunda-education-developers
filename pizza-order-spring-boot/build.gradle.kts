import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val springBootAdminVersion: String by project
val camundaSpringBootVersion: String by project
val camundaVersion: String by project
val hibernateVersion: String by project

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
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-activemq")
    implementation("de.codecentric:spring-boot-admin-starter-client:2.0.2")
    implementation("com.h2database:h2:1.4.197")

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation("org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter:$camundaSpringBootVersion")
    implementation("org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter-rest:$camundaSpringBootVersion")
    implementation("org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter-webapp:$camundaSpringBootVersion")

    implementation("org.hibernate:hibernate-c3p0")

    testImplementation("org.codehaus.groovy:groovy-all")
    testImplementation("org.spockframework:spock-core:1.1-groovy-2.4")
    testImplementation("org.spockframework:spock-spring:1.1-groovy-2.4")
    testImplementation("org.camunda.bpm.extension:camunda-bpm-assert:1.2")
    testImplementation("org.camunda.bpm.extension:camunda-bpm-process-test-coverage:0.3.2")
    testImplementation("org.assertj:assertj-core:2.5.0")

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