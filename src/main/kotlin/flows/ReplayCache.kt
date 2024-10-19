package flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

suspend fun main() {
    val sharedFlow = MutableSharedFlow<Int>(
        replay = 2,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    sharedFlow.onStart {
        println("Started shared flow")
    }.onCompletion {
        println("Completed shared flow")
    }
    val scope = CoroutineScope(Dispatchers.Default)
    with(scope) {
        launch {
            for (it in 1..10) {
                println("Emitting: $it")
                sharedFlow.emit(it)
                delay(1000)
            }
        }
    }

    collectValues(sharedFlow)
    delay(11000)
}

fun collectValues(flow: Flow<*>) {
    val scope = CoroutineScope(Dispatchers.Default + Job())
    scope.launch {
        delay(5000)
        flow.collect { value ->
            println("Collected $value")
        }
    }
}