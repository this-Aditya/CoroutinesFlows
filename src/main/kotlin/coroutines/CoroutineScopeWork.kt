package coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import java.math.BigInteger
import kotlin.system.measureTimeMillis

private val testScope = CoroutineScope(Dispatchers.Default + Job())

fun main() {

    testScope.launch {
        println("Starting task")
        doSomeTask()
        println("Completed task")
    }

    Thread.sleep(7000)
    println("Done")
}

suspend fun doSomeTask() = coroutineScope {
    joinAll( testScope.launch {
        factorialOf(75000, 1)
    })
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
    println("Completed the work:{$num} ComputationTime: $resultComputationTime. Result String conversion time: $stringConversionTime")
    resultString // Return the result string
}