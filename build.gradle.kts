plugins {
    `java-library`
    `maven-publish`
    id("io.freefair.lombok") version "8.0.1"
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    api("org.jetbrains:annotations:24.0.1")
    api("com.zaxxer:HikariCP:5.0.1")
    api("org.mariadb.jdbc:mariadb-java-client:3.1.4")
    api("com.rabbitmq:amqp-client:5.17.0")
    api("redis.clients:jedis:5.0.0-alpha2")
    api("org.json:json:20230227")
    compileOnly("net.kyori:adventure-text-minimessage:4.13.1")
    compileOnly("net.kyori:adventure-text-serializer-gson:4.13.1")
    compileOnly("net.kyori:adventure-text-serializer-plain:4.13.1")
    compileOnly("net.kyori:adventure-text-serializer-legacy:4.13.1")
    compileOnly("io.github.miniplaceholders:miniplaceholders-api:2.1.0")
    compileOnly("net.luckperms:api:5.4")
}

group = "net.trustgames"
version = "0.1-SNAPSHOT"
description = "toolkit"
java.sourceCompatibility = JavaVersion.VERSION_17

publishing {
    repositories {
        maven {
            name = "trustgamesRepoPrivate"
            url = uri("http://81.201.49.240:8080/private")
            isAllowInsecureProtocol = true
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = group.toString()
            artifactId = rootProject.name
            version = version
            from(components["java"])
        }
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}
