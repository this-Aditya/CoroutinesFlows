@file:OptIn(ExperimentalCoroutinesApi::class)

package channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//fun main() = runBlocking {
//    var cur = numbersFrom(2)
//    println("Started current: $cur")
//    repeat(3) {
//        println("::repeat (before cur::receive)")
//        val prime = cur.receive()
//        println("Prime number is: $prime, now filtering.")
//        cur = filter(cur, prime)
//    }
//    coroutineContext.cancelChildren() // cancel all children to let main finish
//}

fun CoroutineScope.numbersFrom(start: Int) = produce<Int> {
    var x = start
    while (true) {
        delay(1000)
        println("::numbersFrom sending: $x")
        send(x++)
    } // infinite stream of integers from start
}

fun CoroutineScope.filter(numbers: ReceiveChannel<Int>, prime: Int) = produce<Int> {
    for (x in numbers) {
        if (x % prime != 0) {
            delay(1000)
            println("Filtering: $x")
            send(x)
        }
    }
}

fun main() = runBlocking<Unit> {
    val producer = doProduceNumbers()
    repeat(5) { launchProcessor(it, producer) }
    delay(950)
    producer.cancel() // cancel producer coroutine and thus kill them all
}

private fun CoroutineScope.doProduceNumbers() = produce<Int> {
    var x = 1 // start from 1
    while (true) {
        send(x++) // produce next
        delay(100) // wait 0.1s
    }
}

fun CoroutineScope.launchProcessor(id: Int, channel: ReceiveChannel<Int>) = launch {
    for (msg in channel) {
        println("Processor #$id received $msg")
    }
}