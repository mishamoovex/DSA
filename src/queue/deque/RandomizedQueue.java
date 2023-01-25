package queue.deque;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

@SuppressWarnings("unchecked")
public class RandomizedQueue<Item> implements Iterable<Item> {

    private final Random random = new Random();
    private Item[] arr;
    private int n = 0;

    public RandomizedQueue() {
        arr = (Item[]) new Object[2];
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        checkNotNull(item);
        if (n == arr.length) {
            resize(arr.length * 2);
        }
        arr[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        checkNotEmpty();
        int randomIndex = uniformInt(n - 1);
        Item item = arr[randomIndex];
        if (randomIndex != n - 1) {
            arr[randomIndex] = arr[n - 1];
        }
        arr[--n] = null;
        if (n > 0 && n == arr.length / 4) {
            resize(arr.length / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        checkNotEmpty();
        int randomIndex = uniformInt(n - 1);
        return arr[randomIndex];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = arr[i];
        }
        arr = copy;
    }

    private void checkNotNull(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    private void checkNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    /**
     * Returns a random integer uniformly in [0, n).
     *
     * @param n number of possible integers
     * @return a random integer uniformly between 0 (inclusive) and {@code n} (exclusive)
     * @throws IllegalArgumentException if {@code n <= 0}
     */
    public int uniformInt(int n) {
        if (n <= 0) throw new IllegalArgumentException("argument must be positive: " + n);
        return random.nextInt(n);
    }


    private class RQueueIterator implements Iterator<Item> {
        private Item[] copy = (Item[]) new Object[arr.length];
        private int copySize = n;

        public RQueueIterator() {
            for (int i = 0; i < arr.length; i++) {
                copy[i] = arr[i];
            }
        }

        @Override
        public boolean hasNext() {
            return copySize > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int rd = uniformInt(copySize);
            Item item = copy[rd];
            if (rd != copySize - 1)
                copy[rd] = copy[copySize - 1];
            copy[copySize - 1] = null;
            copySize--;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator() {
        return new RQueueIterator();
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

    public static void main(String[] args) {
        RandomizedQueue<Integer> custom = new RandomizedQueue<Integer>();
        custom.enqueue(1);
        custom.enqueue(2);
        custom.enqueue(3);
        custom.enqueue(4);

        System.out.println(custom);
        System.out.println(custom.isEmpty());
        System.out.println(custom.size());
        System.out.println(custom.sample());

        custom.dequeue();
        System.out.println(custom);
    }

}