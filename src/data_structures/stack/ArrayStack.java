package data_structures.stack;

import java.util.EmptyStackException;

@SuppressWarnings("unchecked")
public class ArrayStack<E> implements Stack<E> {

    private int count;
    private E[] arr;

    public ArrayStack(int capacity) {
        if (capacity < 6) throw new IllegalArgumentException("Capacity should be grater then 0");
        arr = (E[]) new Object[capacity];
    }

    public ArrayStack() {
        this(6);
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public void push(E elem) {
        if (count >= arr.length) {
            resize(arr.length * 2);
        }
        arr[count++] = elem;
    }

    private void resize(int capacity) {
        E[] copy = (E[]) new Object[capacity];
        for (int i = 0; i < count; i++) {
            copy[i] = arr[i];
        }
        arr = copy;
    }

    @Override
    public E pop() {
        checkNotEmpty();
        E element = arr[--count];
        arr[count] = null;
        if (count > 0 && count == arr.length / 4) {
            resize(arr.length / 2);
        }
        return element;
    }

    @Override
    public E peek() {
        checkNotEmpty();
        return arr[count - 1];
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        } else {
            StringBuilder sb = new StringBuilder().append("[");
            for (int i = 0; i < count; i++) {
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append(arr[i]);
            }
            return sb.append("]").toString();
        }
    }

    private void checkNotEmpty() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
    }
}
