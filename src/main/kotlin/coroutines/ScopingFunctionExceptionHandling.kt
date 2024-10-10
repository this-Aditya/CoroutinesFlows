package coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

//fun main() {
//    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
//        println("Caught $exception")
//    }
//    val scope = CoroutineScope(Job() + exceptionHandler)
//    scope.launch {
//        try {
////    runBlocking {
//            supervisorScope {
//                launch() {
//                    println("Launch builder 1")
//                    delay(1000)
//                    throw RuntimeException()
//                }
//            }
//        } catch (e: Exception) {
//            println("Exception in coroutineScope")
//        }
//    }
//    Thread.sleep(2000)
//    Unit
//}


//
//fun main(){
//    val scope = CoroutineScope(Job())
//    val job = scope.launch {
//        val child = launch {
////            try {
//                println("Child is started")
//            throw RuntimeException("Child is started")
//                delay(Long.MAX_VALUE)
////            } finally {
////                println("Child is cancelled")
////            }
//        }
//        yield()
//        println("Cancelling child")
//        child.cancel()
//        child.join()
//        yield()
//        println("Parent is not cancelled")
//    }
//    scope.launch {
//        job.join()
//    }
//    Thread.sleep(2000)
//}

//fun main() {
//    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
//        println("CoroutineExceptionHandler got $exception")
//    }
//    val scope = CoroutineScope(SupervisorJob() + exceptionHandler)
//    scope.launch {
//
//        launch {
//            println("Started outer first")
//            delay(100)
//            println("Heartbeat 1")
//            delay(200)
//            println("Heartbeat 2")
//            delay(300)
//            println("Heartbeat 3")
//            delay(400)
//            println("Heartbeat 4")
//            delay(500)
//            println("Heartbeat 5")
//            delay(100)
//            println("Heartbeat 6")
//            delay(100)
//            println("Heartbeat 7")
//            delay(100)
//            println("Heartbeat 8")
//            delay(100)
//            println("Heartbeat 9")
//            delay(100)
//            println("Completed outer first")
//        }
//        launch {
//            println("Started outer second")
//            delay(300)
//            throw RuntimeException("Something went wrong")
//            println("Completed outer second")
//        }
//    }
//        scope.launch {
//            launch {
//                println("Started first")
//                delay(1500)
//                println("Completed first")
//            }
//            launch {
//                println("Started second")
//                delay(1200)
//                println("Completed second")
//            }
//        }
//    Thread.sleep(2000)
//}


//fun main() {
//    val scope = CoroutineScope(SupervisorJob())
//
//    scope.launch {
//        launch {
//            try {
//                println("Starting of the first coroutine")
//                delay(1000)
//                println("Completion of the first coroutine")
//            } catch (exception: CancellationException) {
//                println("Caught CancellationException in first coroutine")
//            }
//        }
//        launch {
//            println("Starting of the second coroutine")
//            delay(300)
//            throw RuntimeException()
//        }
//    }
//    Thread.sleep(2000)
//}

//fun main() = runBlocking {
//    val supervisor = SupervisorJob()
//    with(CoroutineScope(coroutineContext + supervisor)) {
//        // launch the first child -- its exception is ignored for this example (don't do this in practice!)
//        val firstChild = launch(CoroutineExceptionHandler { _, e ->
//            println("CoroutineExceptionHandler got $e")
//        }) {
//            launch (){
//                println("The first child is failing")
//                throw AssertionError("The first child is cancelled")
//            }
//        }
//        // launch the second child
//        val secondChild = launch {
//            try {
//                firstChild.join()
//                // Cancellation of the first child is not propagated to the second child
//                println("The first child is cancelled: ${firstChild.isCancelled}, but the second one is still active")
//                delay(Long.MAX_VALUE)
//            } catch (e: Exception) {
//                println("Exception caught: $e")
//            } finally {
//                // But cancellation of the supervisor is propagated
//                println("The second child is cancelled because the supervisor was cancelled")
//            }
//        }
//        // wait until the first child fails & completes
//        firstChild.join()
//        println("Cancelling the supervisor")
//        supervisor.cancel()
//        secondChild.join()
//    }
//}

//fun main() = runBlocking(){
//    val usualScope = CoroutineScope(Job())
//    with(usualScope) {
//        val firstChild = launch(CoroutineExceptionHandler { context, throwable ->
//            println("CoroutineExceptionHandler got $throwable")
//        }) {
//            println("First child is failing...")
//            throw IndexOutOfBoundsException()
//        }
//        val secondChild = launch {
//            try {
//                firstChild.join()
//            } catch (e: Exception) {
//                println("Caught $e")
//            }
//        }
//        try {
//            joinAll(firstChild, secondChild)
//        } catch (e: Exception) {
//            println("Some exception: $e")
//        }
//    }
//}


//fun main() {
//    try {
//        performSomeWork()
//    } catch (e: Exception) {
//        println("Exception: $e")
//    }
//}
//
//fun performSomeWork() = runBlocking(CoroutineExceptionHandler { _, exception ->
//    println("CoroutineExceptionHandler got $exception")
//}) {
//
//        launch(CoroutineExceptionHandler { _, exception ->
//            println("Launch CoroutineExceptionHandler got $exception")
//        }) {
//            throw RuntimeException()
//        }
//    throw ArithmeticException()
//    Unit
//}


fun main() {
    val scope = CoroutineScope(Job())
    scope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
        println("CoroutineExceptionHandler got $coroutineContext: $throwable")
    }) {
        try {
            performTasks(CoroutineExceptionHandler { coroutineContext, throwable ->
                println("CoroutineExceptionHandler got $throwable at child")
            })
        } catch (e: Exception) {
            println("Error: ${e.localizedMessage}")
        }
    }
    Thread.sleep(2000)
}
//
suspend fun performTasks(coroutineExceptionHandler: CoroutineExceptionHandler) {
    coroutineScope {
        launch(coroutineExceptionHandler) {
            delay(1000L)
            throw RuntimeException("Some error")
        }
//        throw ArithmeticException("Exception from supervisor")
    }
}


// 3 main points for the coroutines
/**
 * When an exception is thrown either directly from the coroutineScope or from sub-coroutines of that scope,
 *
 */












//fun main() = runBlocking {
//    val supervisor = SupervisorJob()
//    with(CoroutineScope(coroutineContext + supervisor)) {
//        // launch the first child -- its exception is ignored for this example (don't do this in practice!)
//        val firstChild = launch(CoroutineExceptionHandler { _, _ ->  }) {
//            println("The first child is failing at ${System.currentTimeMillis()/1000}")
//            delay(1000)
//            throw AssertionError("The first child is cancelled")
//        }
//        // launch the second child
//        val secondChild = launch {
//            firstChild.join()
//            // Cancellation of the first child is not propagated to the second child
//            println("The first child is cancelled: ${firstChild.isCancelled}, but the second one is still active at ${System.currentTimeMillis()/1000}")
//            try {
//                delay(Long.MAX_VALUE)
//            } finally {
//                // But cancellation of the supervisor is propagated
//                println("The second child is cancelled because the supervisor was cancelled")
//            }
//        }
//        // wait until the first child fails & completes
//        firstChild.join()
//        delay(100)
//        println("Cancelling the supervisor")
//        supervisor.cancel()
//        secondChild.join()
//    }
//}