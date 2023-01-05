package queue;

import java.util.LinkedList;

public class ListQueue<T> implements Queue<T> {

    private final LinkedList<T> list = new LinkedList<>();

    public ListQueue() {
    }

    public ListQueue(T element) {
        enqueue(element);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public T peek() {
        checkNotEmpty();
        return list.getFirst();
    }

    @Override
    public void enqueue(T elem) {
        list.addLast(elem);
    }

    @Override
    public T dequeue() {
        checkNotEmpty();
        return list.removeFirst();
    }

    @Override
    public String toString() {
        return list.toString();
    }

    private void checkNotEmpty() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
    }
}
