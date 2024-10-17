package flows

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun main() = runBlocking<Unit> {
    withTimeoutOrNull(2500) { // Timeout after 250ms

        simple().collect { value -> println(value) }
        println("Done")
    }
    println("Main")
}

fun simple(): Flow<Int> = flow {
    for (i in 1..5) {
        withContext(NonCancellable) {
            delay(1000)
            println("Emitting $i")
        }
        try {
            emit(i)
        } catch (e: Exception) {
            println("Caught $e")
//            throw e
        }
    }
}