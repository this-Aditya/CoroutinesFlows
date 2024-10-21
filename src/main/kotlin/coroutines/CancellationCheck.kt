package coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext

//suspend fun main() {
//    repeat(10) {
//        println("Performing task number: $it")
//        delay(1000)
//        println("Performed task number: $it")
//    }
//
//delay(4300)
//coroutineContext.job.cancel()
//delay(2000)
//println("Ending program")
//}


suspend fun main() {
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Exception occurred: $exception")
    }
    val scope = CoroutineScope(Job() + exceptionHandler)

    val def = scope.async {
        println("Starting coroutine scope")
        try {
            delay(1000)
        } catch (e: Exception) {
            println("Completed coroutine scope: $e")
        }
//        ensureActive()
        println("Completed coroutine scope")
    }

    runBlocking() {
        println("Starting second coroutine scope")
        delay(500)
        def.cancel()

//        try {
            def.await()
//        } catch (e: Exception) {
//            println("Exception occurred: $e")
//        }
        println("COmpleted")
    }
    delay(1800)
}