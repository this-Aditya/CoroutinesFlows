package flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

suspend fun main() {
    val flow = MutableSharedFlow<Int>(
//        extraBufferCapacity = 5,
//        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val scope = CoroutineScope(Dispatchers.Default)
    scope.launch {
        for (i in 1..5) {
            println("Emitting $i at ${System.currentTimeMillis() / 1000}")
            flow.emit(i)
            delay(1000)
        }
    }
    val job = scope.launch {
        collectEmissions(flow)
    }
//    delay(2000)
//    job.cancel()
    delay(18000)
}

suspend fun collectEmissions(flow: MutableSharedFlow<Int>) {
    flow
        .onEach {
            println("Processing value $it at ${System.currentTimeMillis()/1000}")
            delay(2000)
        }
        .collect { value ->
        println("Processed value $value at ${System.currentTimeMillis()/1000}")
    }
}