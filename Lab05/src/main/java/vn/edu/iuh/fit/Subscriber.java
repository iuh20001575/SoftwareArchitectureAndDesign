package vn.edu.iuh.fit;

import vn.edu.iuh.fit.entity.Item;

import java.util.concurrent.Flow;

public class Subscriber<T> implements Flow.Subscriber<Item<String, T>> {
    private Flow.Subscription subscription;
    private final String name;

    public Subscriber(String name) {
        this.name = name;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(Item item) {
        System.out.printf("+++ Subscriber %s received item: %s from Publisher %s%n", name, item.getItem(), item.getPublisher());
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("onComplete");
    }
}
