package flows

import kotlin.system.measureTimeMillis

class MockSequence {
    fun testWithSequence()  = sequence<TimedInt> {
        var time = 100L;
        for (i in 1..18) {
            measureTimeMillis {
                Thread.sleep(time)
                time += 50
            }.also {
                yield(TimedInt(i, it))
            }
        }
    }

    fun testWithoutSequence() = buildList<TimedInt> {
        var time = 100L
        for (i in 1..13) {
            measureTimeMillis {
                Thread.sleep(time)
                time += 50
            }.also {
                add(TimedInt(i, it))
            }
        }
    }
}

data class TimedInt(val value: Int, val time: Long)

fun main() {

    println("Testing the data without using sequences...")
    val mocking = MockSequence()
    mocking.testWithoutSequence().forEach(::println)

    println("\nTesting the data with sequences....")
    mocking.testWithSequence().forEach(::println)

    println("\nGetting the sequence only")
    mocking.testWithSequence()
    println("Received sequence")
}