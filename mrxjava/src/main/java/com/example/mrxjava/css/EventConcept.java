package com.example.mrxjava.css;

import com.example.mrxjava.functions.Func1;
import com.example.mrxjava.operations.WatchableExtensions;
import com.example.mrxjava.reactive.AbstractIObservable;
import com.example.mrxjava.reactive.IDisposable;
import com.example.mrxjava.reactive.IObservable;
import com.example.mrxjava.reactive.IObserver;

import java.util.Arrays;

public class EventConcept {
    static final String CONTENT = "content";

    public static void main(String[] args) {
        testRealBase();
//        testpullpush();
//        testEmpty();
//        testEvent();
//        testAsync();
    }

    private static void testRealBase() {

        IObservable<String> iObservable = new BaseWatchable<String>(Arrays.asList("1", "2", "3"));
        iObservable.subscribe(new IObserver<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onNext(String args) {
                System.out.println(args);
            }
        });
    }

    private static void testEmpty() {
        WatchableExtensions.empty().subscribe(new IObserver<Object>() {
            @Override
            public void onCompleted() {
                System.out.println("EventConcept.onCompleted");
            }

            @Override
            public void onError(Exception e) {
                System.out.println("EventConcept.onError");
            }

            @Override
            public void onNext(Object args) {
                System.out.println("EventConcept.onNext");
            }
        });
    }

    private static void testEvent() {

        IObservable iObservable = WatchableExtensions.create(new Func1<IDisposable, IObserver<String>>() {
            @Override
            public IDisposable call(IObserver<String> iObserver) {
                //event 装载的地方
                iObserver.onNext("hello world");
//                iObserver.onError(new Exception());
                try {
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                iObserver.onNext("delay");
                iObserver.onCompleted();
                return null;
            }
        });
        IObserver iObserver = new IObserver<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String args) {
                //事件消费的地方
                System.out.println(args);
            }
        };
//        WatchableExtensions.empty().subscribe(iObserver);
        IDisposable onCompleted = iObservable.subscribe(iObserver);//桥梁
//        onCompleted.unsubscribe();//无效

//        iObservable.subscribe(new String(""));

    }

    static void testpullpush() {
        //normal implement
        String dataPull = getDataPull();
        System.out.println(dataPull);//阻塞式的

        //push implement
        IObservable<String> dataPush = getDataPush();
        dataPush.subscribe(new MOnNextImplement<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        });
    }

    static void testAsync() {

    }

    public static String getDataPull() {
        return CONTENT;
    }

    public static IObservable<String> getDataPush() {
        return WatchableExtensions.create(func1);
    }

    static Func1 func1 = new Func1<IDisposable, IObserver<String>>() {
        @Override
        public IDisposable call(IObserver<String> iObserver) {
            iObserver.onNext(CONTENT);
            return null;
        }
    };

    static abstract class MOnNextImplement<T> implements IObserver<T> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onNext(T args) {
            call(args);
        }

        public abstract void call(T t);
    }

    static class BaseIDisposable implements IDisposable {
        private boolean isDisposage = false;

        @Override
        public void unsubscribe() {
            isDisposage = true;
        }

        public boolean isUnsubscribed() {
            return isDisposage;
        }
    }

    static class BaseWatch implements IObserver {
        IObserver observer;
        BaseIDisposable iDisposable;
        private volatile boolean finishRequested = false;
        private volatile boolean finished = false;

        public BaseWatch(IObserver observer, BaseIDisposable iDisposable) {
            this.observer = observer;
            this.iDisposable = iDisposable;
        }

        @Override
        public void onCompleted() {
            if (finished || iDisposable.isUnsubscribed()) {
                // another thread has already finished us, so we won't proceed
                return;
            }
            finishRequested = true;
            synchronized (this) {
                // check again since this could have changed while waiting
                if (finished || iDisposable.isUnsubscribed()) {
                    return;
                }
                observer.onCompleted();
                finished = true;
            }
        }

        @Override
        public void onError(Exception e) {
            if (finished || iDisposable.isUnsubscribed()) {
                // another thread has already finished us, so we won't proceed
                return;
            }
            finishRequested = true;
            synchronized (this) {
                // check again since this could have changed while waiting
                if (finished || iDisposable.isUnsubscribed()) {
                    return;
                }
                observer.onError(e);
                finished = true;
            }
        }

        @Override
        public void onNext(Object args) {
            if (finished || finishRequested || iDisposable.isUnsubscribed()) {
                return;
            }
            synchronized (this) {
                if (finished || finishRequested || iDisposable.isUnsubscribed()) {
                    return;
                }
                observer.onNext(args);
            }
        }
    }

    static class BaseWatchable<T> extends AbstractIObservable {
        Iterable<T> content;

        public BaseWatchable(Iterable<T> content) {
            this.content = content;
        }

        @Override
        public IDisposable subscribe(IObserver observer) {
            BaseIDisposable baseIDisposable = new BaseIDisposable();
            BaseWatch baseWatch = new BaseWatch(observer, baseIDisposable);
            for (T t : content) {
                baseWatch.onNext(t);
            }
            observer.onCompleted();
            return baseIDisposable;
        }
    }


}
