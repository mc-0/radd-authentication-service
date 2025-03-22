plugins {
    java
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.mc0"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly("org.projectlombok:lombok:1.18.30")
    // Spring Boot Web for REST API
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Spring Security with OAuth2 support
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    // JWT support for token generation & validation
    implementation("io.jsonwebtoken:jjwt:0.11.5")

    // Spring Data JPA for user storage
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // PostgreSQL JDBC driver (replace with MySQL if needed)
    implementation("org.postgresql:postgresql")

    // Lombok for reducing boilerplate
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Request validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // DevTools for hot-reloading (optional)
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // Actuator for monitoring (optional)
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // Test dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-test")

}

tasks.withType<Test> {
    useJUnitPlatform()
}
