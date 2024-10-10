package coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }
    val scope = CoroutineScope(Job())

    val deffered = scope.async(exceptionHandler) {
        delay(1000L)
        async {
            throw RuntimeException()
        }
    }

    scope.launch {

            deffered.await()
            try {
            } catch (e: Exception) {
                println("Exception caught $e")
            }
    }
    Thread.sleep(2000L)
}

