import io.reactivex.Observable;
import io.reactivex.Single;

import java.util.ArrayList;
import java.util.List;

// https://stackoverflow.com/questions/56136655/choosing-to-use-either-observable-just-or-single-when-processing-lists
public class StackOverflow0 {
    public static void main(String[] args) {
        List<String> items = new ArrayList<>(10);
        // lets say this array list is already populated
        Observable.fromIterable(items)
                .map(String::length)
                .subscribe(System.out::println);

        Single.just(items)
                .toObservable()
                .flatMapIterable(strings -> strings)
                .map(String::length)
                .subscribe(System.out::println);
    }
}
