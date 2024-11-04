package flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

private val emittingFlow: MutableSharedFlow<String> = MutableSharedFlow<String>(
    replay = 10,
    extraBufferCapacity = 3,
    onBufferOverflow = BufferOverflow.DROP_OLDEST
)

suspend fun main() {
    val job: Job = Job()
    val scope = CoroutineScope(job + Dispatchers.Default)
    val emitterJob = tryEmitData(scope)
    val first = scope.launch {
        emittingFlow.
                collect {
                    println("Collected: $it")
                }
    }

    delay(5000)
    val second = scope.launch {
        emittingFlow.
        collect {
            println("Data: $it")
        }
    }
    delay(5000)
    first.cancel()
    delay(2000)
    second.cancel()
    emitterJob.cancel()
    println("Now cancelling")
    delay(5000)
    println("Finished")
}

fun tryEmitData(scope: CoroutineScope): Job {
    var num = 0
    return scope.launch {
        while (num <= 20) {
            Thread.sleep(1000)
            println("Sending: ${++num}")
            emittingFlow.tryEmit("Updated at :${num}")
        }
    }
}