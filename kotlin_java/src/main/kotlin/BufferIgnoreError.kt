import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.lang.Thread.sleep
import java.util.concurrent.TimeUnit

// what does buffer do with errors?

fun main() {
    val stream = Flowable.create<String>({ emitter ->
        emitter.onNext("hi")
        emitter.onError(IllegalStateException())
    }, BackpressureStrategy.DROP)
        .subscribeOn(Schedulers.io())

    stream
        .buffer(2, TimeUnit.MINUTES)
        .blockingSubscribe({ println("nothing") }, { println("error") })
}
