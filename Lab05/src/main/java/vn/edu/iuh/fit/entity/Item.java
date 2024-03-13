package vn.edu.iuh.fit.entity;

public class Item<T, K> {
    private T publisher;
    private K item;

    public Item(T publisher, K item) {
        this.publisher = publisher;
        this.item = item;
    }

    public T getPublisher() {
        return publisher;
    }

    public void setPublisher(T publisher) {
        this.publisher = publisher;
    }

    public K getItem() {
        return item;
    }

    public void setItem(K item) {
        this.item = item;
    }
}
