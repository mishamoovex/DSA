package stack;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Iterator;

@SuppressWarnings("unchecked")
public class ArrayStack<T> implements Stack<T>, Iterable<T> {

    private int length;
    private int capacity;
    private T[] arr;

    public ArrayStack(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("Capacity should be grater then 0");
        this.capacity = capacity;
        arr = (T[]) new Object[capacity];
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public void push(T elem) {
        if (length == capacity) {
            increaseCapacity();
        }
        arr[length++] = elem;
    }

    private void increaseCapacity() {
        capacity *= 2;
        arr = Arrays.copyOf(arr, capacity);
    }

    @Override
    public T pop() {
        checkNotEmpty();
        T element = arr[--length];
        arr[length] = null;
        return element;
    }

    @Override
    public T peek() {
        checkNotEmpty();
        return arr[length - 1];
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {

            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < length;
            }

            @Override
            public T next() {
                return arr[index++];
            }
        };
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        } else {
            StringBuilder sb = new StringBuilder().append("[");
            for (int i = 0; i < length; i++) {
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
