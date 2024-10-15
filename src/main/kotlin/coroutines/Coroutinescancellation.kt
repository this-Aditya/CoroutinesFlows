package coroutines

import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

fun main() = runBlocking {
    val job = launch {

        repeat(6000000) { i ->
            try {
                println("Performing operation #${i + 1}")
                delay(10L)
            } catch (e: CancellationException) {
                try {
                delay(100L)
                } catch (e: Exception) {
                    println("Exception in exception")
                }
                println("Performing work #${i + 1}")
                someWork()
                throw e
            }
        }
    }

    delay(100L)
    doCancel(job)
}

fun doCancel(job: Job) {
    job.cancel()
}
suspend fun someWork() {
    withContext(NonCancellable) {
        delay(3000L)
        println("Last moment work")
    }
}