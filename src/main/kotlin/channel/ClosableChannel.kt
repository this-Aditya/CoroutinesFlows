package channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//fun main() = runBlocking {
//    val channel = Channel<Int>()
//    launch {
//        for (x in 1..5) {channel.send(x)}
//        channel.close()
//        println("Done!")
//    }
//    for (y in channel) println(y)
//    println("Completed!!")
//}

//fun main() = runBlocking {
//    val channel = Channel<Int>()
//    launch {
//        for (x in 1..7) {
//            delay(1000)
//            channel.send(x)
//        }
//    }
//    for (y in channel) {
//        println("Value: $y")
//        if (y == 4) {
//            channel.close()
//        }
//        println("Received: $y (send: ${channel.isClosedForSend}) || (receive: ${channel.isClosedForSend})")
//    }
//}

@OptIn(DelicateCoroutinesApi::class)
fun main() = runBlocking {
    val channel = Channel<Int>()
    launch {
        for (x in 1..7) {
            if (x == 4) {
//                channel.close()
                channel.cancel()
            }
            println("Executing")
            delay(1000)
            channel.send(x)
        }
    }
    for (y in 1..7) {
        println("Value: $${channel.receive()}")
        println("Received: ${channel.receive()} (send: ${channel.isClosedForSend}) || (receive: ${channel.isClosedForSend})")
    }
}

//@OptIn(ExperimentalCoroutinesApi::class)
//fun main() = runBlocking {
//    var sender: ReceiveChannel<Int>? = null
//    val scope = CoroutineScope(Dispatchers.Unconfined)
//    val job = scope.launch {
//        sender = produce<Int> {
//            for (x in 1..10) {
//                delay(1000)
//                send(x)
//            }
//        }
//    }
//
//    repeat(5) {
//        println(sender?.receive())
//    }
//    println("Done")
//    job.cancel()
////    repeat(5) {
////        println(sender.receive())
////    }
//
//}








