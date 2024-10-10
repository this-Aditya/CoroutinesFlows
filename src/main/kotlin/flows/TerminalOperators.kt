package flows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun emitIntegers() = flow<Int> {
    val delay: Long = 500L
    repeat(5) {
        delay(delay)
        println("Emitting ${it+1}")
        emit(it + 1)
    }
}

fun main() = runBlocking {
    emitIntegers().toList()
    println("Received list")
    emitIntegers().toSet()
    Unit
}