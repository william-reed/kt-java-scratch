import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

fun main() {
    Single.just("hi")
        .subscribeOn(Schedulers.io())
        .doOnSuccess { printThread() }
        .flatMap { Single.just(2) }
        .observeOn(Schedulers.single())
        .doOnSuccess { printThread() }
        .blockingGet()

}

fun printThread() {
    println(Thread.currentThread().name)
}
