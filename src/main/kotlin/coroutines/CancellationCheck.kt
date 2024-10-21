package coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlin.coroutines.coroutineContext

suspend fun main() {
    repeat(10) {
        println("Performing task number: $it")
        delay(1000)
        println("Performed task number: $it")
    }

delay(4300)
coroutineContext.job.cancel()
delay(2000)
println("Ending program")
}