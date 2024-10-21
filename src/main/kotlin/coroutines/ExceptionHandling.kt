package coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//fun main() {
//
//    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
//        println("Caught $exception")
//    }
//    val scope = CoroutineScope(Job())
//
//    val deffered = scope.async(exceptionHandler) {
//        delay(1000L)
//        async {
//            throw RuntimeException()
//        }
//    }
//
//    scope.launch(exceptionHandler) {
//
//        deffered.await()
//        try {
//            } catch (e: Exception) {
//                println("Exception caught $e")
//            }
//    }
//    Thread.sleep(2000L)
//}

fun main() {
    val handler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Caught exception $exception")
    }

    val scope = CoroutineScope(context = SupervisorJob() + Dispatchers.Default)
    var child: Job? = null
    val parent = scope.launch {
        launch {
            println("Started first coroutine")
            delay(1000)
            println("Completed first coroutine")
        }

        child = launch(Dispatchers.IO + handler) {
            println("Started second coroutine: Child? -> ")
            delay(2000)
            throw RuntimeException("Something went wrong")
            println("Completed second coroutine")
        }
        launch {
            println("Started third coroutine")
            delay(3000)
            println("Completed third coroutine")
        }
    }

    Thread.sleep(1000)
    println("Do they share parent child relation: Parent: $parent, Child: $child. Relation exists?: ${parent.job.children.contains(child?.job)}")
    Thread.sleep(3000)
}

