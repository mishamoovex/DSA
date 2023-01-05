import queue.ArrayQueue;
import queue.ListQueue;
import queue.Queue;

public class Main {
    public static void main(String[] args) {

        Queue<Integer> custom = new ArrayQueue<>(3);
        System.out.println(custom);

        custom.enqueue(1);
        custom.enqueue(2);
        custom.enqueue(3);

        System.out.println(custom);

        custom.dequeue();

        System.out.println(custom);

        custom.dequeue();

        System.out.println(custom);

        custom.enqueue(4);

        System.out.println(custom);

        custom.dequeue();

        System.out.println(custom);
    }
}