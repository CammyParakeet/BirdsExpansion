import net.minecrell.pluginyml.paper.PaperPluginDescription
import xyz.jpenilla.runpaper.task.RunServer

plugins {
    java
    `java-library`
    kotlin("jvm") version "2.1.21"
    id("com.gradleup.shadow") version "8.3.5"
    id("net.kyori.indra.git") version "3.1.3"

    // Paper environment
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.17"
    id("net.minecrell.plugin-yml.paper") version "0.6.0"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "com.glance"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("1.21.5-R0.1-SNAPSHOT")

    implementation(kotlin("stdlib-jdk8"))

    //implementation("dev.triumphteam:triumph-gui-paper:4.0.0-SNAPSHOT")

    // Commands
    implementation("org.incendo:cloud-paper:2.0.0-beta.10")
    implementation("org.incendo:cloud-annotations:2.0.0")

    testImplementation(kotlin("test"))
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    runServer {
        minecraftVersion("1.21.6")
    }

    withType<JavaCompile> {
        options.release.set(21)
        options.encoding = Charsets.UTF_8.name()
        options.compilerArgs = listOf("-parameters")
    }

    withType<RunServer> {
        systemProperty("com.mojang.eula.agree", "true")
    }

    test {
        useJUnitPlatform()
    }
}

configure<PaperPluginDescription> {
    name = "BirdsExpansion"

    apiVersion = "1.20"
    version = "Git-${indraGit.commit()?.name?.take(7) ?: "unknown"}"

    main = "com.glance.birds.BirdsExpansion"
}

kotlin {
    jvmToolchain(21)
}