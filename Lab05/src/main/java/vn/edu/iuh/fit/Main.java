package vn.edu.iuh.fit;

import com.github.javafaker.Faker;

public class Main {
    public static void main(String[] args) {
        Faker faker = new Faker();

        Publisher<String> publisher = new Publisher<>("YouTube");
        Publisher<String> publisher2 = new Publisher<>("Facebook");

        Subscriber<String> subscriber1 = new Subscriber<>(faker.name().fullName());
        publisher.subscribe(subscriber1);

        Subscriber<String> subscriber2 = new Subscriber<>(faker.name().fullName());
        publisher.subscribe(subscriber2);
        publisher2.subscribe(subscriber2);

        Subscriber<String> subscriber3 = new Subscriber<>(faker.name().fullName());
        publisher2.subscribe(subscriber3);

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                publisher.publish(faker.artist().name());
                try {
                    Thread.sleep((long) (Math.random() * 5));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                publisher2.publish(faker.artist().name());
                try {
                    Thread.sleep((long) (Math.random() * 5));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();


        System.out.println("End.");
    }
}