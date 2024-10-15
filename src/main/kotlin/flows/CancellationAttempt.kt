package flows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

suspend fun main() {
    val flow = intStream()
    flow.map {
        "Value $it"
    }.catch {
        println("Got an exception $it")
    }.collect {
        if (it == "Value 2") throw RuntimeException("Some error")
        println("Collected $it")
    }
}

fun intStream() = flow<Int> {
    emit(1)
    delay(1000)
    emit(2)
    try {
    } catch (e: Exception) {
        println("Exception $e")
    }
}