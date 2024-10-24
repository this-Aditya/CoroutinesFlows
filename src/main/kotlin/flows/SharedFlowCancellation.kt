package flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

fun main() {
    val scope = CoroutineScope(Dispatchers.Default + Job())
    val flow = MutableStateFlow<Int>(19)

    scope.launch {
        repeat(10) {
            if (it == 0) {
                println("Starting emitting value")
                delay(2000)
                println("Emitting: $it")
                flow.value = it
            } else {
                delay(1000)
                println("Emitting: $it")
                flow.value = it
            }
        }
    }
    Thread.sleep(100)
//    val second = scope.launch {
//        flow.collect {
//            println("Received: $it")
//        }
//    }

    val job = scope.launch {
        flow.collect {
            println("Received: $it")
        }
    }

    Thread.sleep(6000)
    job.cancel()
    println("Cancelled")
    Thread.sleep(6000)
    println("Completing program")
}

