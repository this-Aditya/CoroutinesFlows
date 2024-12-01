package flows

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
testWithSharedFlow()
//testWithStateFlow()
}

suspend fun testWithStateFlow(): Unit = coroutineScope {
    val flow = MutableStateFlow<String>("zero")

    launch {
        delay(1000)
        flow.collect { value ->
            println("Value: $value")
        }
    }

    launch {
        flow.emit("One")
        delay(2000)
        flow.emit("Two")
    }
}

suspend fun testWithSharedFlow(): Unit = coroutineScope {
    val flow = MutableSharedFlow<String>(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    launch {
        delay(1000)
        flow.collect { value ->
            println("Received the value: $value")
        }
    }

    launch {
        flow.emit("One")
        delay(2000)
        flow.emit("Two")
    }
}