package channel

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val scope = CoroutineScope(Job())
    val channel = Channel<Int>()
    val job = scope.launch {
        channel.apply {
            println("Starting send")
            send(1)
            println("Sent 1")
            delay(1000)
            send(2)
            println("Sent 2")
            delay(1000)
            send(3)
            println("Sent 3")
            delay(1000)
            send(4)
            println("Sent 4")
            delay(1000)
//            close()
            send(5)
            println("Sent 5")
        }
    }
    scope.launch {
        channel.apply {
            println("Starting receive")
            println("Received: ${receive()}")
            println("Received: ${receive()}")
            println("Received: ${receive()}")
            println("Received: ${receive()}")
            close()
            val data = try {
                receive()
            } catch (e: Exception) {
                println("Caught $e")
            }
            println("Received: ${receive()}")
        }
    }
    Unit
    job.join()
}