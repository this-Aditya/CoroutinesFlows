package channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.cancellation.CancellationException

//fun main() = runBlocking {
//    val channel = Channel<Int>(UNLIMITED)
//
//    val job = launch {
//        for (i in 1 until 11) {
//            channel.send(i)
//        }
////        try {
//            println("cancelling ${channel.receive()}")
//            channel.cancel()
////        } catch (e: Exception) {
////            println(e)
////        }
////        channel.send(6)
//            try {
//                delay(1000)
//            } catch (e: Exception) {
//                println(e)
//            }
//        println("Completed send: ${coroutineContext.isActive}")
//    }
//        delay(1000)
//    launch {
//     for (i in channel) {
//
//         print("$i ")
//     }
////        channel.receive()
//    }
//    println("Done")
//}



fun main() = runBlocking {
    val channel = Channel<Int>()

    // Sender coroutine
    launch {
        for (i in 1..5) {
            try {
                channel.send(i)
            } catch (e: Exception) {
                println("Error: $e")
            }
            delay(100)  // Simulating some work
        }
    }

    // Receiver coroutine
    val job = launch {
        delay(250)  // Simulating delay before cancel
        println("Cancelling the channel...")
        try {
            channel.cancel()  // Cancelling the channel
            delay(1000)
            println("Is Active? ${coroutineContext.isActive}")
        } catch (e: CancellationException) {
            println("Exception")
        }
    }
    job.join()
//    println("Channel cancelled : ${!job.isActive}")

    // Receiving will throw CancellationException after the channel is cancelled
    try {
        for (i in channel) {
            println("Received: $i")
        }
    } catch (e: Exception) {
        println("Channel cancelled: ${e.message} isActive: ${job.isActive}")
    }
}
