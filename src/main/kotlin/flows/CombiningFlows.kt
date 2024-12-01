package flows

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

val perioden = listOf(
    "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
)

val dagen = listOf(
    "Sunday", "Monday", "Tuesday", "Wednesday",
    "Thursday", "Friday", "Saturday"
)

fun main() = runBlocking<Unit> {
    val days: MutableSharedFlow<String> = MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val months: MutableSharedFlow<String> = MutableSharedFlow(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    launch {
        repeat(2) {
            repeat(12) {
                delay(700)
                emitMonth(months, perioden[it])
            }
        }
    }

    launch {
        repeat(7) {
            delay(1000)
            emitDay(days, dagen[it])
        }
    }

    launch {
        days.combine(months) { day, month ->
            "Day $day, Month $month"
        }.collect(::println)
    }.also { job ->
        delay(17000)
        println("Completed, now stopping!")
        job.cancelAndJoin()
        println("Done!")
    }
}

private fun emitDay(
    flow: MutableSharedFlow<String>,
    string: String
) {
    flow.tryEmit(string)
}

private fun emitMonth(emitIn: MutableSharedFlow<String>, name: String) {
    emitIn.tryEmit(name)
}
