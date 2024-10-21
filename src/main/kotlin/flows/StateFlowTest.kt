package flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

fun main() {
//    val flow = MutableStateFlow<Int>(0)
    val flow = flow<Int> {
        emit(1)
        println("Emitted 1")
        delay(1000)
        emit(2)
        emit(3)
        println("Emitted 2 and 3")
        delay(1000)
        emit(4)
        println("Emitted 4")
        delay(1000)
        emit(5)
        println("Emitted 5")
        delay(1000)
        println("Completed work")
    }.stateIn(GlobalScope, SharingStarted.Eagerly, 0)
    Thread.sleep(2400)
    collectFlow(flow)
//    flow.value = 1
//    Thread.sleep(1000)
//    flow.value = 2
//    flow.value = 3
//    collectFlow2(flow
//    Thread.sleep(1000)
//    flow.value = 4
//    Thread.sleep(1000)
//    flow.value = 5
    Thread.sleep(9000)

    println("Completed Work in main")
}

fun collectFlow(flow: Flow<Int>) {
    val scope = CoroutineScope(Job() + Dispatchers.Default)
    scope.launch {
        flow.onEach {
            println("Collecting value $it")
            delay(1000)
        }
            .collect {
                println("Collected $it")
            }
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