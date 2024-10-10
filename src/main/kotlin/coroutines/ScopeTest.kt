package coroutines

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

fun main() {
    val currentScope = CoroutineScope(Dispatchers.Default + Job())
    val parentScope = CoroutineScope(Dispatchers.Default + Job())

    val topJob = parentScope.launch {
//        joinAll(
        val currentScopeJob = currentScope.launch {
            println("Started")
            supervisorScope {
                launch {
                    println("Starting task 1")
                    delay(1000)
//                    throw RuntimeException("Some Exception Occurred")
                    println("Completed task 1")
                }

                launch {
                    println("Starting task 2")
                    delay(1500)
                    println("Completed task 2")
                }
            }
            launch(context = Job() + Dispatchers.Default + CoroutineName("Task 3")) {
                println("Starting task 3")
                delay(1000)
                println("Completed task 3")
            }
        }
//        )
        launch {
            delay(700)
            println("Starting task 4")
            delay(100)
            println("Completed task 4: 2")
            delay(100)
            println("Completed task 4: 3")
            delay(100)
            println("Completed task 4: 4")
//            println("Is current scope the child scope for runBlockingScope")
//            currentScope.coroutineContext[Job]?.children?.forEach(::println)}
        }
    }

    runBlocking {
        delay(900)
        topJob.cancel()
    }
    Thread.sleep(2000)
    println("Done")
}