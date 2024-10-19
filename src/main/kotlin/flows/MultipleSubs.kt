package flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val flow =
        MutableSharedFlow<Int>(replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    launch {
        println("Emitter started...")
        repeat(6) {
            println("Emitting $it")
            flow.emit(it)
            delay(1000)
        }
    }

    launch {
        delay(4000)
        println("Now creating collector")
        repeat(6) {
            createCollector(flow)
        }
        delay(10000)
    }
    println("Done!")
}

var collectorId = 1
fun createCollector(flow: Flow<Int>) {
    val id = collectorId++
    println("Creating collector with id: $id")
    val scope = CoroutineScope(Dispatchers.Default)

    with(scope) {
        launch {
            flow.collect { value ->
                println("Collected $id collected $value...")
            }
        }
    }
}
