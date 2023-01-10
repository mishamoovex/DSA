package dequeue;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int n = 0;
    private Node<Item> head, tail;

    private static class Node<Item> {
        Item item;
        Node<Item> prev, next;

        public Node(Item item, Node<Item> prev, Node<Item> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        checkNotNull(item);
        if (isEmpty()) {
            head = tail = new Node<>(item, null, null);
        } else {
            head.prev = new Node<>(item, null, head);
            head = head.prev;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        checkNotNull(item);
        if (isEmpty()) {
            head = tail = new Node<>(item, null, null);
        } else {
            tail.next = new Node<>(item, tail, null);
            tail = tail.next;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        checkNotEmpty();
        Node<Item> new_head = head.next;
        Item value = head.item;
        head.item = null;
        head.next = null;
        head = new_head;
        if (head == null) {
            tail.item = null;
            tail = null;
        } else {
            head.prev = null;
        }
        n--;
        return value;
    }

    // remove and return the item from the back
    public Item removeLast() {
        checkNotEmpty();
        Node<Item> new_tail = tail.prev;
        Item value = tail.item;
        tail.item = null;
        tail.prev = null;
        tail = new_tail;
        if (tail == null) {
            head.item = null;
            head = null;
        } else {
            tail.next = null;
        }
        n--;
        return value;
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

    @Override
    public Iterator<Item> iterator() {
        return new Iterator<>() {
            private Node<Item> trav = head;

            @Override
            public boolean hasNext() {
                return trav != null;
            }

            @Override
            public Item next() {
                Item item = trav.item;
                trav = trav.next;
                if (trav == null) {
                    throw new NoSuchElementException();
                }
                return item;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        } else {
            StringBuilder sb = new StringBuilder().append("[");
            Node<Item> trav = head;
            while (trav != null) {
                sb.append(trav.item);
                trav = trav.next;
                if (trav != null) {
                    sb.append(", ");
                }
            }
            return sb.append("]").toString();
        }
    }

    public static void main(String[] args) {
        Deque<Integer> custom = new Deque<>();

        custom.addFirst(1);
        custom.addFirst(2);
        custom.addLast(3);
        custom.addLast(4);

        System.out.println(custom);
        System.out.println(custom.isEmpty());
        System.out.println(custom.size());

        custom.removeFirst();
        custom.removeLast();

        System.out.println(custom);
    }
}
