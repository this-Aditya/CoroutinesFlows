package channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.channels.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalCoroutinesApi::class)
fun main() = runBlocking{
    val values = listOf(1, 2, 3, 4)
    val scope = CoroutineScope(Dispatchers.Default)
    var channel: ReceiveChannel<Int>? = null;
    scope.launch {
        channel = produce(capacity = Channel.UNLIMITED) {
            for (value in values) {
                println("Sending $value")
                send(value)
            }
        }
    }.join()

//    println(channel?.toList() == values)
    println("Channel is closed? ${channel?.isClosedForReceive}")
//    println(channel?.toList() == values)

    check(channel?.toList() == values)
    println("Channel is closed? ${channel?.isClosedForReceive}")

}