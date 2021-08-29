package io.karon

suspend fun main() {
    println("Starting the app")
    App.fetchAndSave()
    println("App finished")
}
