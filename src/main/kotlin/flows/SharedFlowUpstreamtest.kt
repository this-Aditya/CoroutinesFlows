package flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val scope = CoroutineScope(Dispatchers.Default + Job())

    // Replay set to 1, so the last emitted value is retained for collectors
    val sharedFlow = MutableSharedFlow<Int>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    sharedFlow.onCompletion {
        println("Flow completed: $it")
    }

    // Start collecting before emitting any values
    val job = launch {
        sharedFlow
            .collect {
            println("Collected $it")
        }
    }

    launch {
        delay(6000)
        println("Now cancelling")
        job.cancel()
    }

    sharedFlow.emit(1)
    println("Emitted 1")
    delay(1000)
    sharedFlow.emit(2)
    println("Emitted 2")
    delay(1000)
    sharedFlow.emit(3)
    println("Emitted 3")
    delay(1000)
    sharedFlow.emit(4)
    println("Emitted 4")
    delay(1000)
    sharedFlow.emit(5)
    println("Emitted 5")


    job.join()
    launch {
        println("In the second")
        sharedFlow.collect {
            println("Collected $it")
        }
    }

    sharedFlow.emit(4)
    println("Emitted 4")
    delay(1000)
    sharedFlow.emit(5)
    println("Emitted 5")


    delay(3000)
}
