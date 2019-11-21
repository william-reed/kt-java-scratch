import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong

val subject = PublishSubject.create<Int>()
fun main() {
    subject
        .doOnNext { println(it) }
        .zipWith(Observable.error<Int>(Exception()))
        .doOnSubscribe { println("subscribing") }
        .retryWhenDelayed(10, 1, TimeUnit.SECONDS)
        .blockingSubscribe()

}

//fun <T> Single<T>.retryWhenDelayed(maxTries: Int, delay: Long, unit: TimeUnit): Single<T> =
//    retryWhen(retryDelayed(maxTries, delay, unit))

fun <T> Observable<T>.retryWhenDelayed(maxTries: Int, delay: Long, unit: TimeUnit): Observable<T> =
    retryWhen(retryDelayed(maxTries, delay, unit))

private fun retryDelayed(maxTries: Int, delay: Long, unit: TimeUnit) =
    { errors: Observable<Throwable> ->
        println("error occurred")
        val attempts = AtomicLong(0)
        errors
            .doOnNext { subject.onNext(attempts.get().toInt()) }
            .takeWhile { attempts.incrementAndGet() <= maxTries }
            .flatMap { Observable.timer(delay, unit) }
    }
