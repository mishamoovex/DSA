package array;

import java.util.Iterator;

@SuppressWarnings("unchecked")
public class ArrayList<T> implements Iterable<T>, Array<T> {

    private static final int DEFAULT_CAPACITY = 2;

    private T[] arr;
    private int n;

    public ArrayList(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("Illegal capacity:" + capacity);
        arr = (T[]) new Object[capacity];
    }

    public ArrayList() {
        arr = (T[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public boolean isEmpty() {
        return n == 0;
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return arr[index];
    }

    @Override
    public void set(int index, T element) {
        checkElementIndex(index);
        arr[index] = element;
    }

    @Override
    public void clear() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = null;
        }
        n = 0;
    }

    @Override
    public void add(T element) {
        //Time to resize
        if (n == arr.length) {
            resize(arr.length * 2);
        }
        //Add an element to the array and increment number of elements
        arr[n++] = element;
    }

    private void resize(int capacity) {
        T[] copy = (T[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = arr[i];
        }
        arr = copy;
    }

    @Override
    public T removeAt(int index) {
        checkElementIndex(index);
        //Remove
        T data = arr[index];
        arr[index] = null;
        n--;
        //Halve size of the array when the array is one-quarter full. (25% of 100%)
        if (n > 0 && n == arr.length / 4) {
            resize(arr.length / 2);
        }
        return data;
    }

    @Override
    public boolean remove(Object obj) {
        int index = indexOf(obj);
        if (index != -1) {
            removeAt(index);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int indexOf(Object obj) {
        for (int i = 0; i < n; i++) {
            if (arr[i].equals(obj)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    //No concurrent modification check
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < n;
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
            for (int i = 0; i < n; i++) {
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append(arr[i]);
            }
            return sb.append("]").toString();
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= n) {
            throw new IndexOutOfBoundsException();
        }
    }
}
