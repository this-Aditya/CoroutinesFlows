package flows.cachetest

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

fun main() = runBlocking {
    withTimeout(60000) {
        launch {
            val recordsCollector = CatchRecords()
            launch{
                recordsCollector.numberOfRecords.collect {
                    println("Collected ${it.records} records of ${it.name}")
                }
            }
            recordsCollector.start()
        }
    }
    println("Done")
}