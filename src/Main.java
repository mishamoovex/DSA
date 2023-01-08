import queue.NodeQueue;
import queue.Queue;
import stack.NodeBasedStack;
import stack.Stack;

public class Main {
    public static void main(String[] args) {

        Queue<Integer> custom = new NodeQueue<>();

        custom.enqueue(1);
        custom.enqueue(2);
        custom.enqueue(3);

        System.out.println(custom);

        custom.dequeue();

        System.out.println(custom);

        custom.dequeue();

        System.out.println(custom);

        custom.dequeue();

        System.out.println(custom);

    }
}