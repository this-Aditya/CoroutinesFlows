package actor

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor

sealed class CounterMsg
object Increment : CounterMsg()
object GetCounter : CounterMsg()

@OptIn(ObsoleteCoroutinesApi::class)
fun CoroutineScope.counterActor() = actor<CounterMsg> {
    var counter = 0 // Actor's private state

    for (msg in channel) { // Process messages sequentially
        when (msg) {
            is Increment -> {
                counter++
                println("Increment $counter")
            }

            is GetCounter -> println("Counter value: $counter")
        }
    }
}

fun main() = runBlocking {
    val counter: SendChannel<CounterMsg> = counterActor() // Create actor

    // Launch multiple coroutines that send messages to actor
    launch {
        repeat(5) {
            println("First")
            counter.send(Increment)
            delay(1000)
        }
    }
    launch {
        repeat(5) {
            println("Second")
            counter.send(Increment)
            delay(200)
        }
    }

    delay(15000) // Small delay to let increments finish

    // Request counter value
    counter.send(GetCounter)

    counter.close() // Close actor when done
    Unit
}
