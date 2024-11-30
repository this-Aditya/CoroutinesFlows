package flows.cachetest

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class CatchRecords {

    private val first = TestCache("first", 2000)
    private val second = TestCache("second", 2000)
    private val third = TestCache("third", 2000)
    private val fourth = TestCache("fourth", 2000)
    private val fifth = TestCache("fifth", 2000)
    private val sixth = TestCache("sixth", 5000)
    private val seventh = TestCache("seventh", 5000)
    private val eight = TestCache("eight", 5000)
    private val nine = TestCache("nine", 5000)
    private val ten = TestCache("ten", 5000)
    val allCaches: MutableList<TestCache> = mutableListOf(first, second, third, fourth, fifth, sixth, seventh, eight, nine, ten)

    val numberOfRecords = MutableSharedFlow<NamedRecords>(
        replay = 1,
        extraBufferCapacity = 3,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val scope = CoroutineScope(Job() + Dispatchers.Default)

    suspend fun start() = coroutineScope {
        launch {
            println("Starting CacheRecords")
            allCaches.launchJoin {cache: TestCache ->
                cache.numberOfRecords.collect { value ->
                    numberOfRecords.emit(
                        NamedRecords(value.name, value.records)
                    )
                }
            }
            println("Finished CacheRecords")
        }

        allCaches.launchJoin { it.start() }
    }

    suspend inline fun <T> Iterable<T>.launchJoin(
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
        crossinline transform: suspend CoroutineScope.(T) -> Unit,
    ) = coroutineScope {
        forEach { t -> launch(coroutineContext) { transform(t) } }
    }

}