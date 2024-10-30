package coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigInteger
import kotlin.system.measureTimeMillis

fun main() {
    val job = Job()
    val scope = CoroutineScope(Dispatchers.Default + job)

    scope.launch {
        factorialOf(5000, 1)
    }

    scope.launch {
        delay(1000L)
        factorialOf(85000, 1)
    }

    scope.launch {
        delay(900L)
        scope.cancel()
        println("Cancelled")
    }

    scope.launch{
        try {
            delay(3000)
            println("Final task")
        } catch (e: Exception) {
            println("Error: $e")
        }
    }

    Thread.sleep(3000)
    val newscope = CoroutineScope(Dispatchers.Default + Job())

    newscope.launch {
        println("Final")
        factorialOf(10000, 1)
    }

    Thread.sleep(7000L)

}

private fun factorialOf(num: Int, task: Int) {
    var result = BigInteger.ONE
    val resultComputationTime = measureTimeMillis {
        println("Computing factorial of $num")
        for (i in 1..num) {
            result = result.multiply(BigInteger.valueOf(i.toLong()))
        }
    }
    if (task == 2) throw RuntimeException("Task $task is corrupted!")
    var resultString = ""
    val stringConversionTime = measureTimeMillis {
        resultString = result.toString()
    }
    println("Completed the work:Result ComputationTime: $resultComputationTime. Result String conversion time: $stringConversionTime")
    resultString // Return the result string
}