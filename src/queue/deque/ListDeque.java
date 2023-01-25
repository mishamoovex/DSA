package queue.deque;

public class ListDeque<E> implements Deque<E> {

    private int count;
    private Node<E> front, back;

    private static class Node<E> {
        E data;
        Node<E> prev, next;

        public Node(E e, Node<E> prev, Node<E> next) {
            this.data = e;
            this.prev = prev;
            this.next = next;
        }
    }

    public ListDeque() {
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
    public void clear() {
        Node<E> trav = front;
        while (trav != null) {
            Node<E> next = trav.next;
            trav.data = null;
            trav.prev = null;
            trav.next = null;
            trav = next;
        }
        front = back = null;
        count = 0;
    }

    @Override
    public boolean contains(E e) {
        checkNotNull(e);
        for (Node<E> trav = front; trav != null; trav = trav.next) {
            if (trav.data.equals(e)) return true;
        }
        return false;
    }

    @Override
    public boolean offerFirst(E e) {
        checkNotNull(e);
        if (isEmpty()) {
            front = back = new Node<>(e, null, null);
        } else {
            front.prev = new Node<>(e, null, front);
            front = front.prev;
        }
        count++;
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        checkNotNull(e);
        if (isEmpty()) {
            front = back = new Node<>(e, null, null);
        } else {
            back.next = new Node<>(e, back, null);
            back = back.next;
        }
        count++;
        return true;
    }

    @Override
    public E pollFirst() {
        if (isEmpty()) return null;

        Node<E> next = front.next;
        E value = front.data;
        front.data = null;
        front.next = null;
        front = next;

        if (front == null) {
            back.prev = null;
            back.data = null;
            back = null;
        } else {
            front.prev = null;
            if (front.next == null) {
                back.prev = null;
            }
        }
        count--;
        return value;
    }

    @Override
    public E pollLast() {
        if (isEmpty()) return null;

        Node<E> prev = back.prev;
        E value = back.data;
        back.prev = null;
        back.data = null;
        back = prev;
        if (back == null) {
            front.next = null;
            front.data = null;
            front = null;
        } else {
            back.next = null;
            if (back.prev == null) {
                front.next = null;
            }
        }
        count--;
        return value;
    }

    @Override
    public E peekFirst() {
        if (isEmpty()) {
            return null;
        } else {
            return front.data;
        }
    }

    @Override
    public E peekLast() {
        if (isEmpty()) {
            return null;
        } else {
            return back.data;
        }
    }

    private void checkNotNull(E e) {
        if (e == null) throw new NullPointerException("Nullable elements not allowed");
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        } else {
            StringBuilder sb = new StringBuilder().append("[");
            Node<E> trav = front;
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

    public static void main(String[] args) {
        ListDeque<Integer> queue = new ListDeque<>();

        for (int i = 0; i < 3; i++) {
            queue.offerFirst(i);
        }

        System.out.println(queue);

        for (int i = 0; i < 3; i++) {
            queue.offerLast(i);
        }

        System.out.println(queue);

        System.out.println("Is empty: " + queue.isEmpty());
        System.out.println("Size: " + queue.size());
        System.out.println("Contains 2: " + queue.contains(2));
        System.out.println("PeekFirst: " + queue.peekFirst());
        System.out.println("PeekLast: " + queue.peekLast());

        System.out.println("PollFirst: " + queue.pollFirst());
        System.out.println("PollFirst: " + queue.pollFirst());
        System.out.println("PollFirst: " + queue.pollFirst());

        System.out.println(queue);


        System.out.println("PollLast: " + queue.pollLast());
        System.out.println("PollLast: " + queue.pollLast());
        System.out.println("PollLast: " + queue.pollLast());
        System.out.println("PollLast: " + queue.pollLast());

        System.out.println(queue);

    }
}
