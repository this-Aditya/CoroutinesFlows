package coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import java.math.BigInteger
import kotlin.system.measureTimeMillis

private val outerScope: CoroutineScope = CoroutineScope(Dispatchers.Default + Job())
fun main() {
    outerScope.launch {
        val handler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            println("Caught $exception")
        }

        val job: Job = Job()

        var scope = CoroutineScope(job + handler + Dispatchers.Default)

        scope.launch {
            factorialOf(75000)
        }
        println("CP--1")
        delay(1000)
//        job.cancel()
        println("CP--2")
        scope.launch {
            launch {
                delay(1000)
                println("Children CP")
            }
            job.cancel()
            try {
                delay(1000)
            } catch (e: Exception) {
                println("Caught $e")
            }
            println("CP--3: ${job.children.contains(coroutineContext[Job])}")
            factorialOf(75001)
            println("CP--4")
        }
        delay(1000)
        println("CP--5")
    }
    Thread.sleep(7000)
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