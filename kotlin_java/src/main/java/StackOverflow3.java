import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

import java.io.IOException;

//https://stackoverflow.com/questions/56826610/can-i-use-retrywhen-and-return-an-observable-when-a-limit-is-reached/56829381#56829381

public class StackOverflow3 {
    //this is only to simulate the real method that will possibly throw an exception
    private static Observable<String> test() {
        return Observable.error(new IOException());
    }

    public static void main(String[] args) {

        Observable<String> test = test()
                .retryWhen(attempts ->
                        attempts.zipWith(Observable.range(1, 3), (throwable, attempt) -> {
                            if (attempt == 3) {
                                System.out.println("attempting");
                                return false;
                            } else {
                                return true;
                            }
                        }));

Observable.create((ObservableEmitter<String> s) -> s.onError(new RuntimeException("always fails")))
        .retry(3)
        .onErrorResumeNext(throwable -> {
            return Observable.just("hi");
        })
        .subscribe(System.out::println, System.out::println);
    }
}
