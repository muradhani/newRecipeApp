import java.io.ByteArrayOutputStream

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
//
//// Run Git submodule init before including subprojects
//fun initGitSubmodules() {
//    val gitModules = file(".gitmodules")
//    if (gitModules.exists()) {
//        val output = ByteArrayOutputStream()
//        exec {
//            commandLine = listOf("git", "submodule", "status")
//            standardOutput = output
//        }
//        val result = output.toString().trim()
//        if (result.contains("-")) {
//            println("Initializing Git submodules...")
//            exec {
//                commandLine = listOf("git", "submodule", "update", "--init", "--recursive")
//            }
//        } else {
//            println("Git submodules already initialized.")
//        }
//    } else {
//        println(".gitmodules not found; skipping submodule init.")
//    }
//}
//
//initGitSubmodules()

rootProject.name = "newRecipeApp"
include(":app", ":data", ":domain")
project(":data").projectDir = file("data")
project(":domain").projectDir = file("domain")

 