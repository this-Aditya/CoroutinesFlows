package coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

fun main() {
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }

    val scope = CoroutineScope(SupervisorJob() + exceptionHandler)
    scope.launch {
        try {
            doWork()
        } catch (e: Exception) {
            println("Catch Block Got $e")
        }
    }
    Thread.sleep(6000)
}

val customExceptionHandler = CoroutineExceptionHandler { _, exception ->
    println("Custom CoroutineExceptionHandler got $exception")
}

suspend fun doWork() = supervisorScope {

    launch(customExceptionHandler) {
        println("Started launch")
        delay(1000)
        println("Completed task 1")
        delay(1000)
        throw RuntimeException("Some error happened")
        println("Done working for coroutines")
    }

    delay(4000)
//    throw IllegalStateException("Not the correct state of application")
    Unit
}