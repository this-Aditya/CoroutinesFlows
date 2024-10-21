import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

suspend fun main() {
    val flow = MutableSharedFlow<Int>()

    // Start subscribers
    subscriber1(flow)
    subscriber2(flow)
    subscriber3(flow)

    // Delay to allow subscribers to be ready
    delay(100)  // A small delay to ensure subscribers are ready to collect

    println("Emitting value to the flow")
    flow.emit(1)
    println("Broadcasted first value")
    flow.emit(2)
    println("Broadcasted all values to the flow")

    // Delay to allow subscribers to collect emitted values before main coroutine finishes
    delay(4000)
}

fun subscriber1(flow: SharedFlow<Int>): Job {
    val scope = CoroutineScope(Dispatchers.Default + Job())
    return scope.launch {
        flow.onEach {
            println("Started Subscriber 1")
            delay(1000)
        }.collect {
            println("Collected subscriber 1")
        }
    }
}

fun subscriber2(flow: SharedFlow<Int>): Job {
    val scope = CoroutineScope(Dispatchers.Default + Job())
    return scope.launch {
        flow.onEach {
            println("Started Subscriber 2")
            delay(2000)
        }.collect {
            println("Collected subscriber 2")
        }
    }
}

fun subscriber3(flow: SharedFlow<Int>): Job {
    val scope = CoroutineScope(Dispatchers.Default + Job())
    return scope.launch {
        flow.onEach {
            println("Started Subscriber 3")
            delay(3000)
        }.collect {
            println("Collected subscriber 3")
        }
    }
}
