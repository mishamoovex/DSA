package queue;

public interface Queue<T> {

    int size();

    boolean isEmpty();

    void enqueue(T elem);

    T dequeue();

    T peek();
}
