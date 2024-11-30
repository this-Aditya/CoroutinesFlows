package flows.cachetest

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

class TestCache(val name: String, val duration: Long) {
    val numberOfRecords: MutableStateFlow<NamedRecords> = MutableStateFlow(NamedRecords(name, 0))

    suspend fun start() {
        println("Started TestCache: $name")
        repeat(5) {
            delay(duration)
            val currentRecords = duration/1000 * (it+1)
            numberOfRecords.value = NamedRecords(name, currentRecords)
        }
    }
}

data class NamedRecords(
    val name: String,
    val records: Long
)