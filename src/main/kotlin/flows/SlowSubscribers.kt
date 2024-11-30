package flows

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FlowEmitter {
    private val _valueEmitter = MutableSharedFlow<Int>(
        extraBufferCapacity = 2, onBufferOverflow = BufferOverflow.SUSPEND
    )

    val valueEmitter: SharedFlow<Int> = _valueEmitter.asSharedFlow()

    suspend fun start() {
        repeat(5) {
            _valueEmitter.emit(it + 1)
            println("Emitted ${it+1} at ${(System.currentTimeMillis()/1000).toString().takeLast(3)}")
        }
    }
}

class FlowCollector(val name: String, val processingTime: Long, val emitter: FlowEmitter) {
    suspend fun start() {
        emitter.valueEmitter.collect { value ->
            delay(processingTime)
            println("Collector $name collected $value at ${(System.currentTimeMillis()/1000).toString().takeLast(3)}")
        }
    }
}


fun main() = runBlocking {
    val emitter = FlowEmitter()
    val first = FlowCollector("first", 0, emitter)
    val second = FlowCollector("second", 2000, emitter)
    val third = FlowCollector("third", 4000, emitter)
    val fourth = FlowCollector("fourth", 6000, emitter)
    val collectors = mutableListOf(first, second, third, fourth)
    collectors.forEach {
        launch {
            it.start()
        }
    }
    launch { emitter.start() }
    Unit
}

