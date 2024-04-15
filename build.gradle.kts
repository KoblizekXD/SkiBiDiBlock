plugins {
    id("fabric-loom") version "1.6-SNAPSHOT"
    id("java")
    `maven-publish`
}

group = project.ext["maven_group"] as String
version = project.ext["mod_version"] as String

fun fromExt(key: String): String {
    return project.ext[key] as String
}

base {
    archivesName = project.ext["archives_base_name"] as String
}

repositories {
    mavenCentral()
}

loom {
    mods {
        create("biblock") {
            sourceSet("main")
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${fromExt("minecraft_version")}")
    mappings("net.fabricmc:yarn:${fromExt("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${fromExt("loader_version")}")

    // Fabric API. This is technically optional, but you probably want it anyway.
    modImplementation("net.fabricmc.fabric-api:fabric-api:${fromExt("fabric_version")}")
    modImplementation("de.javagl:obj:0.3.0")
    include("de.javagl:obj:0.3.0")
}

tasks.withType<JavaCompile>().configureEach {
    options.release = 17
}

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

//jar {
//    from("LICENSE") {
//        rename { "${it}_${project.base.archivesName.get()}"}
//    }
//}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }

    repositories {

    }
}