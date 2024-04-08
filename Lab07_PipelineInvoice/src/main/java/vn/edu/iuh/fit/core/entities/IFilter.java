package vn.edu.iuh.fit.core.entities;

public interface IFilter<T> {
    T execute(T message);
}
