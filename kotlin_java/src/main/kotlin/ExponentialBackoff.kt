import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import java.lang.Exception
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.pow


fun main() {
    println(
        Single.just(1)
            .zipWith(Single.error<Int>(Exception()))
            .doOnSubscribe { println("subscribing") }
            .exponentialBackoff(2, 10)
            .blockingGet()
    )
}

fun <T> Single<T>.exponentialBackoff(initialDelayMs: Long, maxRetries: Int): Single<T> {
    return retryWhen { errors ->
        val counter = AtomicInteger()
        errors.takeWhile { counter.getAndIncrement() <= maxRetries }
            .flatMap {
                val retryInMs = (initialDelayMs).toDouble().pow(counter.get()).toLong()
                println("retring in $retryInMs ms")
                Flowable.timer(
                    retryInMs,
                    TimeUnit.MILLISECONDS
                )
            }
    }
}
