package flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

fun main() {
    val flow = MutableStateFlow<Int>(0)
    collectFlow(flow)
    flow.value = 1
    Thread.sleep(1000)
    flow.value = 2
    flow.value = 3
//    collectFlow2(flow)
    Thread.sleep(1000)
    flow.value = 4
    Thread.sleep(1000)
    flow.value = 5
    Thread.sleep(2000)
    println("Completed Work")
}

fun collectFlow(flow: StateFlow<Int>) {
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