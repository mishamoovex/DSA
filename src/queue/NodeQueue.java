package queue;

public class NodeQueue<T> implements Queue<T> {

    private int size = 0;
    private Node<T> front, back;

    public static class Node<T> {
        public T data;
        public Node<T> prev, next;

        public Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size < 1;
    }

    @Override
    public void enqueue(T elem) {
        if (isEmpty()) {
            front = back = new Node<>(elem, null, null);
        } else {
            back.next = new Node<>(elem, back, null);
            back = back.next;
        }
        size++;
    }

    @Override
    public T dequeue() {
        checkNotEmpty();
        Node<T> new_front = front.next;
        T removedValue = front.data;
        front.data = null;
        front.next = null;
        front = new_front;
        if (front == null) {
            back.data = null;
            back = null;
        } else {
            front.prev = null;
        }
        size--;
        return removedValue;
    }

    @Override
    public T peek() {
        checkNotEmpty();
        return front.data;
    }

    private void checkNotEmpty() {
        if (size < 1) {
            throw new IllegalStateException("Queue is empty");
        }
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        } else {
            StringBuilder sb = new StringBuilder().append("[");
            Node<T> trav = front;
            while (trav != null) {
                sb.append(trav.data);
                trav = trav.next;
                if (trav != null) {
                    sb.append(", ");
                }
            }
            return sb.append("]").toString();
        }
    }
}
