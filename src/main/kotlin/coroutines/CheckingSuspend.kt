package coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

fun main() {
    val scope =
        CoroutineScope(Job() + CoroutineExceptionHandler { coroutineContext, throwable -> println("Handled exception: $throwable") })

    val scopeJob = with(scope){
        val childJob1 = launch {
            unSuspendingOne()
        }
        val childJob2 = launch {
            unSuspendingOne()
        }
        val childJob3 = launch {
            unSuspendingOne(true)
        }
        val childJob4 = launch {
            unSuspendingOne()
        }
        childJob1.invokeOnCompletion { throwable ->
            if (throwable is CancellationException) {
                println("Cancelled 1")
            }
        }
        childJob2.invokeOnCompletion { throwable ->
            if (throwable is CancellationException) {
                println("Cancelled 2")
            }
        }
        childJob3.invokeOnCompletion { throwable ->
            if (throwable is CancellationException) {
                println("Cancelled 3")
            }
        }
        childJob4.invokeOnCompletion { throwable ->
            if (throwable is CancellationException) {
                println("Cancelled 4")
            }
        }
    }
    Thread.sleep(2500)
}

suspend fun unSuspendingOne(shouldThrow: Boolean = false) {
    delay(500)
    if (shouldThrow) {
        throw RuntimeException("Cancelling the coroutine task as explicit exception is thrown")
    }
    println("Performing work on non suspending method")
    delay(200)
    println("Performed work on non suspending method")
}