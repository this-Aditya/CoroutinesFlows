package channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    getSendChannelException()
}

fun getSendChannelException() = runBlocking() {
    val scope = CoroutineScope(Dispatchers.Default)
    val channel = Channel<Int>()
    scope.launch {
        channel.apply {
            for (x in 1..5) {
                println("Sent $x")
                delay(1000)
                send(x)
            }
        }
    }
    repeat(5) {
        println("Receive")
        val data = channel.receive()
        println("Received data: $data")
        if (data == 3) channel.close()
    }
}

fun getReceiveChannelException() {

}