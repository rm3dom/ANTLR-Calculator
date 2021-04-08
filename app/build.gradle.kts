
plugins {
    id("kotlin-application-conventions")
}

dependencies {
    implementation(project(":calculator"))
}

application {
    // Define the main class for the application.
    mainClass.set("org.example.gka.app.AppKt")
}
