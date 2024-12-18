package coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.math.BigInteger
import kotlin.system.measureTimeMillis

fun main() = runBlocking(){
    val context = Dispatchers.Default
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }
    val scope = CoroutineScope(exceptionHandler + context + SupervisorJob())

    val job = scope.launch {
        println("In launch")
        try {
            val x = withContext(Dispatchers.Default) {
                println("in withContext")
                withContext(scope.coroutineContext) {
                    println("Staring the sub-context")
                    factorialOf(30000)
                    println("Completing the sub context")
                }
                println("Completed sub with context")
                val fact = factorialOf(130000)
                println("out withContext")
                fact
            }
            println("Result is: $x")
        } catch (e: Exception) {
            println("Exception got $e")
        }
        println("out launch")
    }

    scope.launch {
        delay(2000)
        println("Now cancelling")
        job.cancel()
    }
    delay(8000)
    println("Quitting")
}

private fun CoroutineScope.factorialOf(num: Int) {
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
    resultString // Return the result string
}