package flows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

suspend fun main() {
    val firstFlow = flow<Int> {
        var currentDelay = 200L
        repeat(15) {
            delay(currentDelay)
            emit(it)
            currentDelay += 50
        }
    }

    val secondFlow = flow<Int> {
        println("Returning the square from 1 to 5 in the proceeding calls...")
        val delay = 500L
        repeat(5) {
            delay(delay)
            emit(it * it)
        }
        println("\nNow emitting the values from other flow")
        emitAll(firstFlow)
    }

    secondFlow.collect {
        println("Collected $it")
    }
}