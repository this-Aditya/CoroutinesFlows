package coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
//    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
//        println("Caught exception: ${exception.localizedMessage}")
//        throw exception // Re-throwing would crash the program
//    }
    val scope = CoroutineScope(Job() + Dispatchers.Default)

    val job = scope.launch {
        delay(1000L)
        throw ArithmeticException("Something went wrong")
    }

    scope.launch {
        job.join()
        println("Done")
    }

    Thread.sleep(2000L)
}