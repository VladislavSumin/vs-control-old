package ru.vs.control

fun commonMain() {
    val scope = ServerScope()
    println("Hello server")
    scope.blockingAwait()
}
