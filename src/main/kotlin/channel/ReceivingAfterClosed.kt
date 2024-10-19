package channel

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    val channel = Channel<Int>(UNLIMITED)
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }
    val scope = CoroutineScope(Job() + exceptionHandler)

    scope.launch {
        println("Started")
        val sendJob = launch {
            for (i in 1..15) {
                channel.send(i)
                println("Sent: $i")
                delay(1000)
            }
        }
        delay(5000)
        sendJob.cancel()
        channel.close()
        val receiveJob = launch {
            for (i in channel) {
                println("Received: $i")
            }
            println("Exitted")
        }
    }
    Thread.sleep(15000)
    Unit
}