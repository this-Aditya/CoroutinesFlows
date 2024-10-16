package channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(){

    val flow = callbackFlow<String> {
//        println("Started callback flow")
        trySendBlocking("one")
//        println("One")
        send("two")
//        println("Two")
        trySendBlocking("three")
//        println("Three")
//        close()
        awaitClose {
            println("Closing the resources used in callback flow")
        }
        println("Completed callback flow")
    }


    val scope = CoroutineScope(Job())

    scope.launch {
        flow.onEach {
            delay(1000)
            println("OnEach : $it")
        }.map {
            delay(1000)
            println("Map : $it")
            "Mapped $it"
        }.collect {
            println("Collected: $it")
        }
    }
    scope.launch {
        delay(4300)
        scope.cancel()
    }
    Thread.sleep(6000)
    println("Completed progeann")
}
