package coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigInteger
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.system.measureTimeMillis

private val outerScope = CoroutineScope(Dispatchers.Default + Job() + CoroutineExceptionHandler { coroutineContext, throwable ->
    println("Exception in outer scope: $throwable")
})
fun main() {
    var ans: String? = ""
    outerScope.launch {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            println("Caught $exception")
            throw exception
        }

        val job = SupervisorJob()

        val scope = CoroutineScope(job + exceptionHandler + Dispatchers.Default)

        scope.launch {
            suspendCoroutine<String> {
                println("Started calculating factorial")
                ans = factorialOf(85000)
                println("Completed calculating factorial")
                it.resumeWithException(RuntimeException("Something went wrong"))
            }
            println("After suspend coroutine")
            println("Result from continuation: ")
        }.join()

        scope.launch {
            delay(1000)
            println("finalized")
        }
    }

    Thread.sleep(5000)
}


private fun CoroutineScope.factorialOf(num: Int): String {
    var result = BigInteger.ONE
    val resultComputationTime = measureTimeMillis {
        println("Computing factorial of $num")
        for (i in 1..num) {
            result = result.multiply(BigInteger.valueOf(i.toLong()))
        }
    }
    var resultString = ""
    val stringConversionTime = measureTimeMillis {
        resultString = result.toString()
    }
    println("Completed the work:Result ComputationTime: $resultComputationTime. Result String conversion time: $stringConversionTime")
    return resultString // Return the result string
}