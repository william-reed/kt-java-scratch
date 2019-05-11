import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.ReplaySubject
import java.util.concurrent.TimeUnit

// what happens when we try and buffer but we are on a single thread?
// this just spins forever.

fun main() {
    val subject = ReplaySubject.create<Int>()

    println(Schedulers.trampoline())
    RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
    RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    RxJavaPlugins.setInitIoSchedulerHandler { Schedulers.trampoline() }

    subject.onNext(1)
    subject.onNext(2)
    subject.onNext(3)
    subject.onNext(4)

    subject.buffer(5L, TimeUnit.SECONDS)
        .blockingSubscribe(::println)
}
