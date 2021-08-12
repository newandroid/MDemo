package com.example.mrxjava.css;

import com.example.mrxjava.functions.Func1;
import com.example.mrxjava.operations.WatchableExtensions;
import com.example.mrxjava.reactive.IDisposable;
import com.example.mrxjava.reactive.IObservable;
import com.example.mrxjava.reactive.IObserver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateTest {
    private static class MIDisposable implements IDisposable {
        boolean isInterrupte = false;

        @Override
        public void unsubscribe() {
            isInterrupte = true;
        }
    }

    public static void main(String[] args) {
        IObserver<String> iObserver = new IObserver<String>() {
            @Override
            public void onCompleted() {
                System.out.println("CreateTest.onCompleted");
            }

            @Override
            public void onError(Exception e) {
                System.out.println("CreateTest.onError:" + e.getMessage());
            }

            @Override
            public void onNext(String args) {
                System.out.println("CreateTest.onNext:" + args);
            }
        };

        Func1<IDisposable, IObserver<String>> func1 = new Func1<IDisposable, IObserver<String>>() {
            @Override
            public IDisposable call(IObserver<String> s) {
                final MIDisposable m = new MIDisposable();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        System.out.println("main thread:" + Thread.currentThread().getName());
                        for (int i = 0; i < 10; i++) {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                            if (!m.isInterrupte) {
                                s.onNext("hello:" + i + " " + simpleDateFormat.format(System.currentTimeMillis()));
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    s.onError(e);
                                }
                            }
                        }
                        s.onCompleted();
                    }
                }).start();


                return m;
            }
        };
        IObservable<String> css = WatchableExtensions.create(func1);
        final IDisposable subscribe = css.subscribe(iObserver);
        try {
            Thread.sleep(5 * 1000);
            subscribe.unsubscribe();
            System.out.println("sub thread:" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
