package com.example.mrxjava.css;

import com.example.mrxjava.functions.Func1;
import com.example.mrxjava.operations.WatchableExtensions;
import com.example.mrxjava.reactive.IDisposable;
import com.example.mrxjava.reactive.IObserver;

public class AsyncConcept {
    public static void main(String[] args) {
        WatchableExtensions.create(new Func1<IDisposable, IObserver<String>>() {
            @Override
            public IDisposable call(IObserver<String> iObserver) {
                final Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 75; i++) {
                            iObserver.onNext("i" + i);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        iObserver.onCompleted();
                    }
                });
                thread.start();
                return new IDisposable() {
                    @Override
                    public void unsubscribe() {
                        thread.interrupt();
                    }
                };
            }
        }).subscribe(new IObserver<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onNext(String args) {
                System.out.println(Thread.currentThread().toString() + " content:" + args);
            }
        });
    }
}
