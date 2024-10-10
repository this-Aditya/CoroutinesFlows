package coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    val scope = CoroutineScope(Job()+ CoroutineExceptionHandler { coroutineContext, throwable -> println("Handled exception: $throwable") })

    val scopeJob = scope.launch {
        unSuspendingOne()
        unSuspendingOne()
//        try {
            unSuspendingOne(true)
//        } catch (e: Exception) {
//            println("Exception thrown $e")
//        }
        unSuspendingOne()
    }
    scopeJob.invokeOnCompletion { throwable ->
        if (throwable is RuntimeException) {
            println("Stopping things in between ")
        }
    }
    Thread.sleep(2500)
}

suspend fun CoroutineScope.unSuspendingOne(shouldThrow: Boolean = false) {
    delay(500)
    if (shouldThrow) {
        throw RuntimeException("Cancelling the coroutine task as explicit exception is thrown")
    }
    println("Performing work on non suspending method")

}