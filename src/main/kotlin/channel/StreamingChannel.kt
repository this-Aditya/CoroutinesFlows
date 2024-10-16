package channel

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun main() = runBlocking {
    val numbers = produceNumbers() // produces integers from 1 and on
    println("Proceding to squares: at ${System.currentTimeMillis() / 1000}")
    val squares = square(numbers) // squares integers
    println("Procedded squares: at ${System.currentTimeMillis() / 1000}")
    repeat(5) {
        println(squares.receive()) // print first five
    }
    println("Done!") // we are done
    coroutineContext.cancelChildren() // cancel children coroutines
}

fun CoroutineScope.produceNumbers() = produce<Int> {
    var x = 1
    while (true) {
        delay(1000)
        println("Producing number: $x at ${System.currentTimeMillis() / 1000}")
        send(x++)
    } // infinite stream of integers starting from 1
}

fun CoroutineScope.square(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
    for (x in numbers) {
        delay(1000)
        println("Producing Square: ${x*x} at ${System.currentTimeMillis() / 1000}")
        send(x * x)
    }
}