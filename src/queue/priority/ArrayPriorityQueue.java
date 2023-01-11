package queue.priority;

import java.util.Arrays;

@SuppressWarnings("unchecked")
public class ArrayPriorityQueue<T extends Comparable<T>> implements PriorityQueue<T> {

    private int heapSize = 0;
    private T[] heap;

    public ArrayPriorityQueue(T[] data) {
        heap = data;
        heapSize = data.length;

        for (int i = Math.max(0, (heapSize / 2) - 1); i >= 0; i--) {
            sink(i);
        }
    }

    @Override
    public int size() {
        return heapSize;
    }

    @Override
    public boolean isEmpty() {
        return heapSize == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < heapSize; i++) {
            heap[i] = null;
        }
        heapSize = 0;
    }

    @Override
    public T peek() {
        checkNotEmpty();
        return heap[0];
    }

    @Override
    public T poll() {
        checkNotEmpty();
        return removeAt(0);
    }

    @Override
    public void add(T element) {
        checkNotNull(element);
        if (heapSize == heap.length) {
            resize(heap.length * 2);
        }
        heap[heapSize] = element;
        swim(heapSize++);
    }

    @Override
    public T removeAt(int i) {
        checkNotEmpty();
        checkElementIndex(i);
        //Get the return data
        T removedData = heap[i];
        //Swap the removable item with the last item and decrease the heap size
        swap(i, --heapSize);
        //Remove item from the heap
        heap[heapSize] = null;
        //If happened that we just removed the last item
        if (i == heapSize) return removedData;
        //Use this element to check if the sinking has change the node
        T tmp = heap[i];
        sink(i);
        if (heap[i].equals(tmp)) {
            swim(i);
        }
        //Resize the heap if needed
        if (heapSize > 0 && heapSize == heap.length / 4) {
            resize(heap.length / 2);
        }
        return removedData;
    }

    @Override
    public boolean remove(T element) {
        checkNotEmpty();
        checkNotNull(element);
        int index = indexOf(element);
        if (index != -1) {
            return removeAt(index) != null;
        } else {
            return false;
        }
    }

    @Override
    public int indexOf(T element) {
        checkNotNull(element);
        for (int i = 0; i < heapSize; i++) {
            if (heap[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(T element) {
        checkNotNull(element);
        return indexOf(element) != -1;
    }

    private void sink(int k) {
        while (true) {
            int left = 2 * k + 1;  //Left node
            int right = 2 * k + 2; //Right node
            int smallest = left;   //Assume left is the smallest node of the two children

            //Find is smaller left or right
            //If right is smaller set smallest to be right
            if (right < heapSize && less(right, left)) {
                smallest = right;
            }

            //Stop if we're outside the bounds of the tree
            //or stop early if we cannot sink k anymore
            if (left >= heapSize || less(k, smallest)) break;

            //Move  down the tree following the smallest node
            swap(smallest, k);
            k = smallest;
        }
    }

    private void swim(int k) {
        //Grab the index of the next parent node
        int parent = (k - 1) / 2;
        //Keep swimming while we have not reached the root
        //and while we're less than our parent.
        while (k > 0 && less(k, parent)) {
            //Swap k with the parent
            swap(k, parent);
            //Reset k index
            k = parent;
            //Grab the index of the next parent node
            parent = (k - 1) / 2;
        }
    }

    private void resize(int capacity) {
        T[] copy = (T[]) new Object[capacity];
        for (int i = 0; i < heapSize; i++) {
            copy[i] = heap[i];
        }
        heap = copy;
    }

    private boolean less(int i, int j) {
        T nodeI = heap[i];
        T nodeJ = heap[j];
        return nodeI.compareTo(nodeJ) <= 0;
    }

    private void swap(int i, int j) {
        T nodeI = heap[i];
        T nodeJ = heap[j];
        heap[i] = nodeJ;
        heap[j] = nodeI;
    }

    private void checkNotEmpty() {
        if (isEmpty()) {
            throw new IllegalStateException("Empty");
        }
    }

    private void checkNotNull(T element) {
        if (element == null) {
            throw new IllegalArgumentException();
        }
    }

    private void checkElementIndex(int i) {
        if (i < 0 || i >= heapSize) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        } else {
            StringBuilder sb = new StringBuilder().append("[");
            for (int i = 0; i < heapSize; i++) {
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append(heap[i]);
            }
            return sb.append("]").toString();
        }
    }

    //Recursively checks if this heap is a min heap
    //This method is just for testing purposes to make
    //sure the heap invariants is still being maintained
    //Called this method with k=0 to start at the root
    @Override
    public boolean isMinHeap(int k) {
        //If we are outside the bounds of the heap return true
        if (k >= heapSize) return true;

        int left = 2 * k + 1;
        int right = 2 * k + 2;

        if (left < heapSize && !less(k, left)) return false;
        if (right < heapSize && !less(k, right)) return false;

        //Recurse on both children to make sure they're also valid heaps
        return isMinHeap(left) && isMinHeap(right);
    }

    public static void main(String[] args) {
        Integer[] data = new Integer[10];
        data[0] = 2;
        data[1] = 3;
        data[2] = 1;
        data[3] = 10;
        data[4] = 4;
        data[5] = 9;
        data[6] = 5;
        data[7] = 7;
        data[8] = 6;
        data[9] = 8;

        System.out.println(Arrays.toString(data));

        ArrayPriorityQueue<Integer> queue = new ArrayPriorityQueue<>(data);

        System.out.println(queue);
        System.out.println(queue.isMinHeap(0));


    }
}
