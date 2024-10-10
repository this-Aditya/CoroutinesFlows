package flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

suspend fun main() {
    val collection = integerStream()
    val scope = CoroutineScope(Job())
    scope.launch {
        collection.map {
            if (it == 2) {
                cancel()
            }
            "Value: $it"
        }.catch {
            println("Caught exception: $it")
        }.onCompletion {
            if (it is CancellationException) {
                println("Flow is cancelled")
            }
        }.collect {
            println("Collected $it")
        }
    }.join()
}

fun integerStream() = flow<Int> {
    emit(1)
    delay(1000)
    emit(2)
//    try {
//        delay(1000)
        emit(3)
//    } catch (e: CancellationException) {
//        println("Caught CancellationException")
//    }
}