package coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

val coroutineContext: CoroutineContext = Dispatchers.Default
val job: Job = SupervisorJob()
val scope: CoroutineScope = CoroutineScope(coroutineContext + job)

fun main() {
    val one = scope.launch {
        println("Starting coroutine scope-1")
        delay(1000)
        println("Completed Coroutine scope-1")
    }

    val two = scope.launch {
        println("Starting coroutine scope-2")
        delay(2000)
        println("Is first one completed? ${one.isCompleted}")
        delay(1000)
        println("Completed Coroutine scope-2")
    }

    val third = scope.launch {
        println("Starting coroutine scope-3")
        delay(4000)
        println("Is second one active? ${two.isActive}")
        delay(1000)
        println("Completed Coroutine scope-3")
    }
    scope.launch {
        println("Starting coroutine scope-4")
        delay(1100)
        println("Now cancellinng whole")
        scope.cancel()
        ensureActive()
        println("Cancelled whole")
        println("Is third one active? ${third.isActive} , $isActive")
    }
    Thread.sleep(6000)
    println("Is third one active? ${third.isActive}")
    println("Terminating coroutine scope")
}