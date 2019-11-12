import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong
import kotlin.random.Random


fun main() {
    println(
        Single.just(1)
            .zipWith(Single.error<Int>(Exception()))
            .doOnSubscribe { println("subscribing") }
            .exponentialBackoff()
            .blockingGet()
    )
}

/**
 * Exponential backoff
 *
 * @param initialDelayMs initial time to wait before retrying, and value to base [multiplier] on
 * @param maxElapsedTimeMs the maximum elapsed time to wait (since the first attempt) before stopping any further retries
 * @param multiplier number to multiply previous delay by. for example 1.5 would be a 1.5x or 150% increase
 * @param randomization randomization factor. for example .1 would be a value 10% above or below the normal value
 */
fun <T> Single<T>.exponentialBackoff(
    initialDelayMs: Long = 50,
    maxElapsedTimeMs: Long = TimeUnit.MILLISECONDS.convert(2, TimeUnit.MINUTES),
    multiplier: Double = 1.5,
    randomization: Double = .5
): Single<T> {
    return retryWhen { errors ->
        val prevValue = AtomicLong(initialDelayMs)
        val start = System.currentTimeMillis()
        errors.takeWhile { (System.currentTimeMillis() - start) <= maxElapsedTimeMs }
            .flatMap {
                val retryInMs = prevValue.updateAndGet { (it * multiplier).toLong() }
                print("re-trying in $retryInMs ms")
                val randomizedRetryInMs =
                    Random.nextDouble(retryInMs * randomization, retryInMs * (1 + randomization)).toLong()
                println(" randomized to $randomizedRetryInMs")
                Flowable.timer(
                    randomizedRetryInMs,
                    TimeUnit.MILLISECONDS
                )
            }
    }
}
