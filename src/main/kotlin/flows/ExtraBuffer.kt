package flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

private val flow = MutableSharedFlow<String>(2, 3, BufferOverflow.SUSPEND)
suspend fun main() {
    val scope = CoroutineScope(Dispatchers.Default + Job())
    emitData(scope)
    scope.launch {
        println("Now starting")
        flow.collect {
            if (it == "Emitting: 1") {
                delay(13000)
            }
            println("Collected: $it")
        }
    }
    delay(20000)
    println("Done")
}

fun emitData(scope: CoroutineScope): Job {
    var num = 0
    return scope.launch {
        while (num <= 20) {
            delay(1000)
            println("Emitted: ${++num}")
            flow.emit("Emitting: $num")
        }
    }
}