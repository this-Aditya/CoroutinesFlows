package coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

suspend fun main() {
    val mainScope = CoroutineScope(Dispatchers.Default + Job())
    val job = mainScope.launch {

        try {
            emitterPart().collect {
                println("Collecting $it")
            }
        } catch (e: Exception) {
            println("Exception while collecting $e")
        }
    }
    delay(12000)
    job.cancel()
    delay(10000)
    println("Completed")
}

fun emitterPart(): MutableSharedFlow<Int> {
    val scope = CoroutineScope(Dispatchers.Default + Job())
    val flow = MutableSharedFlow<Int>()
    scope.launch {
        repeat(20) { i ->
            delay(1000)
            println("Emitted: $i")
            flow.emit(i)
        }
        println("Emitting finished")
    }
    return flow
}