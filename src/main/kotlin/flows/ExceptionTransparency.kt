package flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): kotlin.Unit = runBlocking(){
    val scope = CoroutineScope(Dispatchers.Default)
    scope.launch {
        try {
            simpleStream().collect {
                println("Collected: $it")
            }
        } catch (e: Exception) {
            println("Error: $e")
        }

    }
    delay(11000)
}

fun simpleStream(): Flow<String> = flow {
    repeat(10) {
        delay(1000)
        println("Emitting $it")
        try {
            emit(it)
        } catch (e: Exception) {
            println("Caught $e")
        }
    }
}.map {
    println("Mapping $it")
    if (it == 5) throw RuntimeException("Error")
    "Value: $it"
}