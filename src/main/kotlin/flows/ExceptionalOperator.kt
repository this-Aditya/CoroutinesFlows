package flows;

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

//suspend fun main() {
//    var i = 0;
//    numberEmitter()
//        .map {
//            println("In the map ")
//            if (i == 1) {
//                throw RuntimeException("Exception while mapping data ")
//            }
//            "Modified value: $it"
//        }
//        .catch {
//            println("Caught $it")
//        }
//        .collect {
//            println("In the collect")
//            i++;
//            val num = it;
//            println("Received: $num")
////            throw RuntimeException("Some exception")
//        }
//}

//fun numberEmitter() = flow<Int> {
//    emit(1)
//    delay(1000)
////    try {
//        emit(2)
////    } catch (e: Exception) { println("Exception: $e") }
//    delay(1000)
////    try {
//    emit(3)
////    }catch (e: Exception) { println("Error on 3rd try ") }
//}

//fun main() {
//    val scope = CoroutineScope(Dispatchers.Default + Job())
//    scope.launch {
//        flow<Int> {
//            try {
//            emit(1)
//            } catch (e: Exception) {
//                println("Flow Error: $e")
//            }
//        }.map {
//            throw Exception("Collected $it")
//            "Value: $it\n"
//        }.catch {
//            println("Handled exception in catch operator")
//        }.map {
//            throw Exception("received $it")
//            "Value: $it\n"
//        }.collect {
//            println("In collect block")
//            throw Exception("Collected $it")
//        }
//    }
//    Thread.sleep(2000)
//}


fun main() {
    val scope = CoroutineScope(Job())
    var i = 0
    scope.launch {
        flow<Int> {
            emit(1)
            delay(1000)
            emit(2)
            delay(1000)
            try {
                emit(3)
            } catch (e: Exception) {
                println("Exception: $e")
            }
        }.map {
            println("Mapping data $it")
            if (++i == 2) throw Exception("Error")
            "Value: $it"
        }.catch {
            println("Some error happened: ${it.message}")
        }.collect  {
            println("Received data: $it")
        }
    }
    Thread.sleep(4000)
}



























