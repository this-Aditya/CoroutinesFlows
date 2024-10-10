package flows

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

//fun main() {
//    val integerFlow = getIntegerStream()
//    integerFlow
//        .filter { it > 5 }
//        .take(10)
//        .onEach { println("Received $it") }
//        .launchIn(CoroutineScope(EmptyCoroutineContext))
//    Thread.sleep(20000)
//}
//
//fun getIntegerStream() = flow<Int> {
//    repeat(15) {
//        delay(1000)
//        println("Emitting ${it + 1}")
//        emit(it + 1)
//    }
//}


//suspend fun main() {
//    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
//        println("Exception Handler $exception")
//    }
//    val scope = CoroutineScope(Job())
//    scope.launch(exceptionHandler) {
//        try {
//            coroutineScope {
//                launch() {
//                    delay(1000)
//                    throw RuntimeException("Some exception happened")
//                }
//                Unit
//            }
//        } catch (e: Exception) {
//            println("Exception Handler $e")
//        }
//    }
//    delay(1300)
//}


fun main() {
    val scope = CoroutineScope(Job())
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("ExceptionHandler got $exception")
    }
    scope.launch(exceptionHandler) {
        try {
            launch {
                someExceptionalFunction()
            }
        } catch (e: Exception) {
            println("Received error $e")
        }
    }
    Thread.sleep(1300)
}


suspend fun someExceptionalFunction() {
    println("Started executing...")
    delay(1000L)
    println("Executing...")
    throw ArithmeticException("Exception thrown....")

}














