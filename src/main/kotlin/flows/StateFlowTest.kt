package flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun main() {
    val flow = MutableStateFlow<Int>(0)
//    val flow = flow<Int> {
    collectFlow(flow)
    flow.value = 1
    println("Emitted 1")
    Thread.sleep(1000)
    flow.value = 2
    flow.value = 3
    println("Emitted 2 and 3")
    Thread.sleep(1000)
    flow.value = 4
    println("Emitted 4")
    Thread.sleep(1000)
    flow.value = 5
    println("Emitted 5")
    Thread.sleep(1000)
//    println("Completed work")
//    }.stateIn(GlobalScope, SharingStarted.Eagerly, 0)
//    Thread.sleep(2400)
    flow.value = 6
    Thread.sleep(1000)
    flow.value = 7
    flow.value = 8
//    collectFlow2(flow
    Thread.sleep(1000)
    flow.value = 9
    Thread.sleep(1000)
    flow.value = 10
    Thread.sleep(9000)

    println("Completed Work in main")
}

fun collectFlow(flow: StateFlow<Int>) {
    val scope = CoroutineScope(Job() + Dispatchers.Default)
    val collectorJob = scope.launch {
        launch {
            val firstFlow = try {
                flow.onEach {
//                    ensureActive()
                    println("Collecting value $it")
                }
            } catch (e: Exception) {
                println("One:  Collecting error $e")
                flow
            }

            try {
                firstFlow.collect {
                    println("Collected $it")
                }
            } catch (e: Exception) {
                println("Two:  Collecting error $e")
            }
        }
    }

    scope.launch {
        delay(4200)
        println("Cancelling collector")
        collectorJob.cancel()
        println("Cancelled collector")
    }
}

//fun collectFlow2(flow: StateFlow<Int>) {
//    val scope = CoroutineScope(Job() + Dispatchers.Default)
//    scope.launch {
//        flow.onEach {
//            println("Collecting value $it in second cope")
//            delay(1000)
//        }
//            .collect {
//                println("Collected $it")
//            }
//    }
//}