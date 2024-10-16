package channel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.cancellation.CancellationException

@OptIn(ExperimentalCoroutinesApi::class)
fun main() = runBlocking {
    val channel = produce<Int> {
        send(1)
        send(2)
        try {
            println("Sending third")
            send(3) // will throw CancellationException
        } catch (e: CancellationException) {
            println("The channel was cancelled!")
                throw e // always rethrow CancellationException
        }
    }
    println(channel.receive() == 1)
    println(channel.receive() == 2)
    channel.cancel()
    println("Is closed: ${channel.isClosedForReceive}")
}