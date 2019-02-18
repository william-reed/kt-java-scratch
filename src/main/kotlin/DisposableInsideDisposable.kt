import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.lang.Thread.sleep
import java.util.concurrent.TimeUnit

// if we have nested disposables what happens to the inner disposable?
fun main() {
    val source = Observable.just("hi")
    val source1 = Observable.interval(1, TimeUnit.SECONDS)

    var innerDisposable: Disposable?

    val disposable0 = source
        .doOnNext {
            innerDisposable = source1
                .subscribe(::println)
        }
        .subscribe(::println)

    println("sleeping 2 seconds")
    sleep(2000)
    println("disposable outers")
    disposable0.dispose()

    sleep(10000)
}

// conclusion: disposing of the outer does not affect the inner subscription which makes senses
