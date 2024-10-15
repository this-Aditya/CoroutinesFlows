package coroutines

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import java.math.BigInteger
import java.util.Scanner
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val scanner = Scanner(System.`in`)
    print("Factorial of? ")
    val ans = findFact(scanner.nextInt())
    // Await the result from the async coroutine
    val resp = ans.await()
    println("Answer Received: ${resp.substring(0..10)}")
// Here, ans is of type Deferred<String>, and no waiting has occurred.
    println("This prints immediately")

}

suspend fun findFact(num: Int): Deferred<String> = coroutineScope {
    // Return async directly
    val ref = async {
        var result = BigInteger.ONE
        val resultComputationTime = measureTimeMillis {
            for (i in 1..num) {
                result = result.multiply(BigInteger.valueOf(i.toLong()))
            }
        }
        var resultString = ""
        val stringConversionTime = measureTimeMillis {
            resultString = result.toString()
        }
        println("Completed the work: Result ComputationTime: $resultComputationTime. Result String conversion time: $stringConversionTime")
        resultString // Return the result string
    }

    println("End of scope: $ref")
    ref
}
