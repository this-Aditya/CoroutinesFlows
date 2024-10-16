package channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun streamingNumbers(scope: CoroutineScope) {
    scope.launch {
        val numbers = produceNumbers(10)
        val result = pipeline(numbers)

        println( result.receive().toString())
    }
}

// Producing numbers, each number being sent to the pipeline
fun CoroutineScope.produceNumbers(count: Int): ReceiveChannel<Int> = produce {
    for (i in 1..count) {
        println("First method -- producing $i")
        send(i)
    }
}

// Pipeline which process the numbers
fun CoroutineScope.pipeline(
    numbers: ReceiveChannel<Int>
): ReceiveChannel<Int> = produce {
    // Filtering out even numbers
    val filtered = filter(numbers) { it % 2 != 0 }

    // Squaring the remaining odd numbers
    val squared = map(filtered) { it * it }

    // Summing them up
    val sum = reduce(squared) { acc, x -> acc + x }

    send(sum)
}

fun CoroutineScope.filter(
    numbers: ReceiveChannel<Int>,
    predicate: (Int) -> Boolean
): ReceiveChannel<Int> = produce {
    numbers.consumeEach { number ->
        if (predicate(number)) send(number)
    }
}

fun CoroutineScope.map(
    numbers: ReceiveChannel<Int>,
    mapper: (Int) -> Int
): ReceiveChannel<Int> = produce {
    numbers.consumeEach { number ->
        send(mapper(number))
    }
}

fun reduce(
    numbers: ReceiveChannel<Int>,
    accumulator: (Int, Int) -> Int
): Int = runBlocking {
    var result = 0
    for (number in numbers) {
        result = accumulator(result, number)
    }
    result
}
