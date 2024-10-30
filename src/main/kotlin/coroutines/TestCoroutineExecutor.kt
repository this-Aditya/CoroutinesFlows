package coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import java.math.BigInteger
import kotlin.system.measureTimeMillis

suspend fun main() {

    var future1: CoroutineWorkExecutor.CoroutineFutureHandle? = null
    var future2: CoroutineWorkExecutor.CoroutineFutureHandle? = null
    var future3: CoroutineWorkExecutor.CoroutineFutureHandle? = null

    val handler = CoroutineWorkExecutor(Dispatchers.Default).also { it.start() }

    println("CP 1")
    future1 = handler.delay(2000) {
        println("Inside-CP: 1")
        factorialOf(75000, 1)
    }
    println("CP 2")
    future2 = handler.delay(1000) {
        future1?.awaitNow()
        println("Inside-CP: 2")
        factorialOf(75001, 1)
    }
    println("CP 3")
    future3 = handler.delay(4000) {
        println("Inside-CP: 3")
        factorialOf(75002, 1)
    }

    delay(15000L)
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