package coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun main() {
    println("Starting main")
    val scope = CoroutineScope(Dispatchers.Default + Job())

    scope.launch {

    }
}