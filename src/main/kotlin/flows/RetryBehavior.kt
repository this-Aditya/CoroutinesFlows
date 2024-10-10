package flows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.runBlocking

suspend fun main() = runBlocking {
    val stream = getIntegerStream()
    var i = 0;

    stream.map {
        if (it == 4) throw RuntimeException("Some exception")
        "Value: $it"
    }.catch {
        println("Caught: ${it.message}")
    }.retry {
        it is RuntimeException
    }.collect {
        println("Collected $it")
    }
}


fun getIntegerStream() = flow<Int> {
    repeat(6) {
        delay(1000)
        emit(it)
    }
}