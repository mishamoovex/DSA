package queue;

public interface Queue<T> {

    public void enqueue(T elem);

    public T dequeue();

    public T peek();

    public int size();

    public boolean isEmpty();
}
