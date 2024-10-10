import com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val i = AtomicInteger(0)

    // Use the coroutine scope to launch coroutines
    withContext(Dispatchers.Default) {
        // Launch a set of coroutines that perform the work in parallel
        val time = measureTimeMillis {
            repeat(10000) {
                joinAll(
                launch {
                    println("Working on task: ${i.incrementAndGet()}")

                    // Launch child coroutines for deeper task levels
                    launch() {
                        println("Working on children task: ${i.incrementAndGet()}")

                        // Launch another level of coroutines
                        launch() {
                            println("Working on sub-children task: ${i.incrementAndGet()}")
                        }
                    }
                }
                )
            }
        }
        println("Finished the work completely in: $time ms")
    }

}
