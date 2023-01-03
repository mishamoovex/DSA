import java.util.Iterator;

@SuppressWarnings("unchecked")
public class CustomArray<T> implements Iterable<T> {

    private T[] arr;
    private int length;   //length user thinks array is
    private int capacity; //actual array size

    public CustomArray() {
        this(16);
    }

    public CustomArray(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("Illegal capacity:" + capacity);
        this.capacity = capacity;
        arr = (T[]) new Object[capacity];
    }

    public int size() {
        return length;
    }

    private boolean isEmpty() {
        return size() != 0;
    }

    public T get(int index) {
        if (index >= length) throw new IndexOutOfBoundsException();
        return arr[index];
    }

    public void set(int index, T element) {
        if (index >= capacity) throw new IndexOutOfBoundsException();
        arr[index] = element;
    }

    public void clear() {
        for (int i = 0; i < capacity; i++) {
            arr[i] = null;
        }
        length = 0;
    }

    public void add(T element) {
        //Time to resize
        if (length + 1 >= capacity) {
            //Increase the array capacity
            if (capacity == 0) {
                capacity = 1;
            } else {
                capacity *= 2;
            }
            //Create a new array with increased capacity
            T[] new_arr = (T[]) new Object[capacity];
            //Copy elements from the old array to the new one
            if (length >= 0) System.arraycopy(arr, 0, new_arr, 0, length);
            //Replace the array with the new one
            arr = new_arr;
        }
        //Add an element to the array and increment length
        arr[length++] = element;
    }

    public T removeAt(int index) {
        if (index >= length) throw new IndexOutOfBoundsException();
        T data = arr[index];

        //Initialize a new array with
        T[] new_arr = (T[]) new Object[length - 1];
        //Copy elements from the old array to the new one
        for (int i = 0, j = 0; i < length; i++, j++) {
            //Skip over the remove index by fixing j
            if (i == index) {
                j--;
            } else {
                new_arr[j] = arr[i];
            }
        }
        //Replace the array with the new one
        arr = new_arr;
        //Update length and capacity
        capacity = --length;
        return data;
    }

    public boolean remove(Object obj) {
        for (int i = 0; i < length; i++) {
            if (arr[i].equals(obj)) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }

    public int indexOf(Object obj) {
        for (int i = 0; i < length; i++) {
            if (arr[i].equals(obj)) {
                return i;
            }
        }
        return -1;
    }

    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {

            int index = 0;

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
        if (length == 0) {
            return "[]";
        } else {
            StringBuilder sb = new StringBuilder(length).append("[");
            for (int i = 0; i < length - 1; i++) {
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append(arr[i]);
            }
            return sb.append("]").toString();
        }
    }
}
