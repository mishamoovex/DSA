package queue;

@SuppressWarnings("unchecked")
public class CircularQueue<E> implements Queue<E> {

    private final E[] arr;
    private int size;
    private int front;
    private int back;

    public CircularQueue(int capacity) {
        arr = (E[]) new Object[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = null;
        }
        size = 0;
        front = back = 0;
    }

    @Override
    public boolean contains(E e) {
        checkNotNull(e);
        for (E element : arr) {
            if (element.equals(e)) return true;
        }
        return false;
    }

    @Override
    public boolean offer(E e) {
        checkNotNull(e);
        if (size >= arr.length) return false;
        arr[back++] = e;
        back = adjustIndex(back, arr.length);
        size++;
        return true;
    }

    @Override
    public E poll() {
        if (isEmpty()) return null;
        E e = arr[front];
        arr[front++] = null;
        front = adjustIndex(front, arr.length);
        size--;
        return e;
    }

    @Override
    public E peek() {
        if (isEmpty()) return null;
        return arr[front];
    }

    private int adjustIndex(int index, int size) {
        return index >= size ? index - size : index;
    }

    private void checkNotNull(E e) {
        if (e == null) throw new NullPointerException("Nullable values not allowed");
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        } else {
            StringBuilder sb = new StringBuilder().append("[");
            for (int i = 0; i < arr.length; i++) {
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append(arr[i]);
            }
            return sb.append("]").toString();
        }
    }

    public static void main(String[] args) {
        CircularQueue<Integer> queue = new CircularQueue<>(10);

        for (int i = 0; i < 10; i++) {
            queue.offer(i);
        }

        System.out.println(queue);
        System.out.println("Peek: " + queue.peek());
        System.out.println("Contains 2: " + queue.contains(2));
        System.out.println("Size: " + queue.size());
        System.out.println("Is not empty: " + queue.isEmpty());
        System.out.println("Poll: " + queue.poll());
        System.out.println("Poll: " + queue.poll());
        System.out.println("Poll: " + queue.poll());
        System.out.println("Poll: " + queue.poll());
        System.out.println("Size: " + queue.size());
        System.out.println("Offer 13: " + queue.offer(13));
        System.out.println("Offer 14: " + queue.offer(14));
        System.out.println(queue);
        queue.clear();
        System.out.println(queue);
    }
}
