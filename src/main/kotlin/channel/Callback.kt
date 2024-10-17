package channel

import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.runBlocking

suspend fun ProducerScope<String>.fetchDataListener(callback: (String) -> Unit) {
    // Simulate callback-based data fetching
    callback("Data fetched at: " + System.currentTimeMillis())
    delay(3000)
    callback("Data fetched at: " + System.currentTimeMillis())
    delay(3000)
    callback("Data fetched at: " + System.currentTimeMillis())
    delay(3000)
    println("Finalizing")
    close()
}

fun getDataUpdatesFlow() = callbackFlow {
    val listener = { data: String ->
        trySend(data) // Emit data to the flow
        Unit
    }

    fetchDataListener(listener)

    awaitClose {
        // Clean up or unregister the callback if needed
        println("Data flow collection cancelled.")
    }
}

fun main() = runBlocking {
    val flow = getDataUpdatesFlow()
    val data = flow.first()
    println(data)
    flow.onCompletion {
        println("Completed")
    }
    delay(1000)
}
