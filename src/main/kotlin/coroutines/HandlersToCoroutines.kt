package coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import java.math.BigInteger
import kotlin.system.measureTimeMillis

fun main() {

}


suspend fun mockSafeHandlerCompute() {
    coroutineScope {
        val time = measureTimeMillis {
            println("Step 1")
            findFactorialWithResult(5000)
        }
        var attempts: Int = 0
        if (time < 15000) {
            attempts = 15000/time.toInt()
        }
        for (attempt in 1..attempts) {
            println("Step ${attempt + 1}")
            findFactorialWithResult(5000)
        }
    }
}

private fun findFactorialWithResult(num: Int): String {
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

private fun findFactorialWithoutResult(num: Int){
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
}