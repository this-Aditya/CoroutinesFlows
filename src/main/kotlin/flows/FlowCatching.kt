package flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val flow = flow {
        for (i in 1..10) {
            delay(1000)
            try {
                emit(i + 1)
            } catch (e: Exception) {
                println("error while emit: $e")
            }
        }
    }

    val scope = CoroutineScope(Dispatchers.Default)
    launch {

//        try  catch (e: Exception) {
//            println("Exception $e")
//        }
            flow.onEach {
                println("Received $it")
            }.catch {
                println("Some exception ${it.message}")
            }
                .collect {
                if (it == 5) throw Exception("error")
                println("Collected $it")
            }
    }

    launch {
        repeat(14) {
            delay(1000)
            println("Tick: $it")
        }
    }

    println("Done")
}
