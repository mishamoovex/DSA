package queue;

@SuppressWarnings("unchecked")
public class ArrayQueue<T> implements Queue<T> {

    private int front;
    private int back;
    private int numberOfElements;
    private final T[] data;

    public ArrayQueue(int capacity) {
        data = (T[]) new Object[capacity];
        front = 0;
        back = 0;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void enqueue(T elem) {
        checkNotFull();
        data[back++] = elem;
        back = adjustIndex(back, data.length);
        numberOfElements++;
    }

    @Override
    public T dequeue() {
        checkNotEmpty();
        T elem = data[front];
        data[front++] = null;
        front = adjustIndex(front, data.length);
        numberOfElements--;
        return elem;
    }

    @Override
    public T peek() {
        checkNotEmpty();
        return data[front];
    }

    private int adjustIndex(int index, int size) {
        return index >= size ? index - size : index;
    }

    private void checkNotEmpty() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
    }

    private void checkNotFull() {
        if (numberOfElements == data.length) {
            throw new RuntimeException("Queue is full");
        }
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        } else {
            StringBuilder sb = new StringBuilder().append("[");
            for (int i = 0; i < data.length; i++) {
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append(data[i]);
            }
            return sb.append("]").toString();
        }
    }
}
