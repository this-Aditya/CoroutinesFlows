package coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {
    println("Entered main function")
    Thread.sleep(2000)
    runBlocking {
        println("In the runBlocking block")
        delay(2000)
        println("Finalizing the runblocking block")
    }
    println("Finalizing the main function")
}