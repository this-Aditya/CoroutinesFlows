package channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    val channel: Channel<Int> = Channel<Int>()

    val scope = CoroutineScope(Dispatchers.Default)

    scope.launch {
        launch {
            val value = channel.receive()
            println("Received $value from channel")
        }

        launch {
            delay(1000)
            channel.send(1)
        }
    }
    Thread.sleep(4000)
}