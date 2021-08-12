package css.com.applab.rxjava2;

import android.view.View;

import androidx.annotation.CheckResult;

import org.junit.Test;
import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import kotlin.Unit;

import static androidx.core.util.Preconditions.checkNotNull;

public class RxUpStreamTest {
    private ObservableEmitter<String> totoGun;
    private Observable o = Observable.create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
            emitter.onNext("up stream");
            totoGun = emitter;
        }
    });

    @Test
    public void hoh() {
        if(!totoGun.isDisposed()){
            totoGun.onNext("other msg");
        }
//        o.debounce()
//        s();
//        totoGun.onNext("other msg");
//        Observable.just("1", "2", "3").subscribe(System.out::println);
    }

    public void s() {
        o.subscribe(System.out::println);
    }

    @CheckResult @NonNull
    public static Observable<Void> clicks(@NonNull View view) {
        checkNotNull(view, "view == null");
        return Observable.create(new ViewClickOnSubscribe(view));
    }
    final static class ViewClickOnSubscribe implements ObservableOnSubscribe<Void> {
        final View view;

        ViewClickOnSubscribe(View view) {
            this.view = view;
        }

        @Override
        public void subscribe(@NonNull ObservableEmitter<Void> subscriber) throws Exception {
//            verifyMainThread();

            View.OnClickListener listener = new View.OnClickListener() {
                @Override public void onClick(View v) {
                    if (!subscriber.isDisposed()) {
                        subscriber.onNext(null);
                    }
                }
            };

//            subscriber.add(new MainThreadSubscription() {
//                @Override protected void onUnsubscribe() {
//                    view.setOnClickListener(null);
//                }
//            });

            view.setOnClickListener(listener);
        }
    }
}
