package channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    rendezvousChannel(CoroutineScope(Dispatchers.Default))
    Thread.sleep(4000)
}
fun rendezvousChannel(
    coroutineScope: CoroutineScope
) {
    // create a rendezvous channel with capacity 0
    val channel = Channel<Int>()

    // get the starting time to display the time difference in the logs
    val startTime = System.currentTimeMillis()

    // launch the producer coroutine
    coroutineScope.launch {
        for (i in 1..5) {
            log( "Producer -> Sending $i", startTime)
            channel.send(i) // send data to the channel
            log( "Producer -> Sent $i", startTime)
            delay(1)
        }
        channel.close() // close the channel after sending all data
    }

    // launch the consumer coroutine
    coroutineScope.launch {
        // iterate over the channel until it's closed
        for (value in channel) {
            log("Consumer Received $value", startTime)
        }
    }
}

// To log the message and time
fun log(message: String, startTime: Long) {
    val currentTime = System.currentTimeMillis()
    val diffTime = String.format("%.3f", (currentTime - startTime).toDouble() / 1000)
    println("[$diffTime] $message")
}
