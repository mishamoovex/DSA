package queue;

public class LinkedQueue<E> implements Queue<E> {

    private int size;
    private Node<E> front, back;

    public static class Node<T> {
        public T data;
        public Node<T> prev, next;

        public Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    public LinkedQueue(){}

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size < 1;
    }

    @Override
    public void clear() {
        Node<E> trav = front;
        while (trav != null) {
            Node<E> tmp = trav.next;
            trav.prev = null;
            trav.next = null;
            trav.data = null;
            trav = tmp;
        }
        size = 0;
        front = null;
        back = null;
    }

    @Override
    public boolean contains(E e) {
        checkNotNull(e);

        for (Node<E> trav = front; trav != null; trav = trav.next)
            if (trav.data.equals(e)) {
                return true;
            }
        return false;
    }

    @Override
    public boolean offer(E e) {
        checkNotNull(e);

        if (isEmpty()) {
            front = back = new Node<>(e, null, null);
        } else {
            back.next = new Node<>(e, back, null);
            back = back.next;
        }
        size++;
        return true;
    }

    @Override
    public E poll() {
        if (isEmpty()) return null;

        Node<E> new_front = front.next;
        E removedValue = front.data;
        front.data = null;
        front.next = null;
        front = new_front;
        //Checks if there was only one item in the list before removal
        //If its true -> remove the back too
        if (front == null) {
            back.data = null;
            back = null;
        }
        //Otherwise remove the prev link to the removed object from the new front
        else {
            front.prev = null;
            //Also check if there is only one item remaining in the collection
            //If it is we also need to remove prev link from the back
            if (front.next == null) {
                back.prev = null;
            }
        }
        size--;
        return removedValue;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        } else {
            return front.data;
        }
    }

    private void checkNotNull(E e){
        if (e == null) throw new NullPointerException("Nullable values not allowed");
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
        LinkedQueue<Integer> queue = new LinkedQueue<>();

        for(int i = 0; i < 10; i ++ ){
            queue.offer(i);
        }

        System.out.println(queue);
        System.out.println("Peek: " + queue.peek());
        System.out.println("Contains 2: " + queue.contains(2));
        System.out.println("Size: " + queue.size());
        System.out.println("Is not empty: " + queue.isEmpty());
        System.out.println("Poll: " + queue.poll());
        System.out.println("Size: " + queue.size());
        System.out.println(queue);
        queue.clear();
        System.out.println(queue);
    }
}
