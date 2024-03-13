package vn.edu.iuh.fit;

import vn.edu.iuh.fit.entity.Item;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class Publisher<T> implements Flow.Publisher<Item<String, T>> {
    private final SubmissionPublisher<Item<String, T>> publisher = new SubmissionPublisher<>();
    private final String name;

    public Publisher(String name) {
        this.name = name;
    }

    public void publish(T t) {
        System.out.printf("~~~ Publisher %s add new item: %s%n", name, t);

        publisher.submit(new Item<>(name, t));
    }

    @Override
    public void subscribe(Flow.Subscriber<? super Item<String, T>> subscriber) {
        publisher.subscribe(subscriber);
    }
}
