import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

// multiple subscribeOn leads to what?
// just the first one applies

fun main() {
    val a = Single.just("hello")
        .doOnSuccess { println(Thread.currentThread().name) }

    a.subscribeOn(Schedulers.io())
        .blockingGet()
}
