package ru.vs.control

object HelloProvider {
    private const val HELLO_STRING = "Hello from common module"
    fun getHello(): String {
        return HELLO_STRING
    }
}
