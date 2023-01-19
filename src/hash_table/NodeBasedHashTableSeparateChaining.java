package hash_table;

@SuppressWarnings("unchecked")
public class NodeBasedHashTableSeparateChaining<K, V> implements Hashable<K, V> {

    private static class EntryNode<K, V> {
        final int hash;
        K key;
        V value;
        EntryNode<K, V> next;

        public EntryNode(K key, V value, EntryNode<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
            hash = value.hashCode();
        }

        @Override
        public String toString() {
            return key + " => " + value;
        }

        //We are not overriding the Object equals method
        //no casting is required with this method
        public boolean equals(EntryNode<K, V> other) {
            if (hash != other.hash) return false;
            return key.equals(other.key);
        }
    }

    private static final int DEFAULT_CAPACITY = 3;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    private final double maxLoadFactor;
    private int capacity;
    private int threshold;
    private int size = 0;

    private EntryNode<K, V>[] table;


    public NodeBasedHashTableSeparateChaining() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public NodeBasedHashTableSeparateChaining(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    public NodeBasedHashTableSeparateChaining(int capacity, double maxLoadFactor) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Invalid capacity");
        }

        if (maxLoadFactor <= 0 || maxLoadFactor >= 1) {
            throw new IllegalArgumentException("Invalid maxLoadFactory");
        }

        this.maxLoadFactor = maxLoadFactor;
        this.capacity = Math.max(capacity, DEFAULT_CAPACITY);
        threshold = (int) (capacity * maxLoadFactor);
        table = (EntryNode<K, V>[]) new EntryNode[capacity];
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
        for (int i = 0; i < table.length; i++) {
            clearBucket(table[i]);
            table[i] = null;
        }
        size = 0;
    }

    private void clearBucket(EntryNode<K, V> head) {
        while (head != null) {
            EntryNode<K, V> next = head.next;
            head.next = null;
            next.value = null;
            head = next;
        }
    }

    @Override
    public boolean containsKey(K key) {
        checkKeyNotNull(key);
        return bucketSeekEntry(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        checkValueNotNull(value);

        for (EntryNode<K, V> bucket : table) {
            EntryNode<K, V> trav = bucket;
            while (trav != null) {
                if (trav.value.equals(value)) {
                    return true;
                }
                trav = trav.next;
            }
        }

        return false;
    }


    @Override
    public V put(K key, V value) {
        //Validate if there no nullable input data
        checkKeyNotNull(key);
        checkValueNotNull(value);
        //Generates a table index by the hash code from the given key
        int bucketIndex = normalizeIndex(key.hashCode());
        //Gets the bucket by the hash index from the table
        //If there is no value with the given key or any bucket that produces the same
        //hash code (hash collision) -> returns null
        EntryNode<K, V> bucket = table[bucketIndex];
        //Search for an entry by the given key inside the bucket
        if (bucket != null) {
            //Traverse through the bucket until the node with the given key will be found
            EntryNode<K, V> trav = bucket;
            //We need to keep track the tail position to be able to add a new node
            //if the item by the given key wasn't found in the bucket
            //Tail position should be before traversal node
            EntryNode<K, V> tail = null;
            while (trav != null) {
                //Update the tail position
                tail = trav;
                //If an entry by the given key was found -> update value and return removed one
                if (trav.key.equals(key)) {
                    V removedValue = trav.value;
                    trav.value = value;
                    return removedValue;
                }
                trav = trav.next;
            }
            //Add a new node at the end of the bucket list
            tail.next = new EntryNode<>(key, value, null);
        }
        //Otherwise create a new bucket with the given key and value
        else {
            table[bucketIndex] = new EntryNode<>(key, value, null);
            if (++size >= threshold) resizeTable();
        }
        //Returns null if a new item has been added to the table
        return null;
    }

    private void resizeTable() {
        capacity *= 2;
        threshold = (int) (maxLoadFactor * capacity);

        EntryNode<K, V>[] copy = (EntryNode<K, V>[]) new EntryNode[capacity];

        for (int i = 0; i < table.length; i++) {

        }
    }

    private EntryNode<K,V> copyBucket(EntryNode<K,V> origin){
        EntryNode<K,V> damy = new  EntryNode()
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    /**
     * Converts hash value to an index. Essentially, this strips the
     * negative sign and places the hash value in the domain [0, capacity]
     * <p>
     * 0x7FFFFFFF it's an efficient way to clear out bits of register.
     * In this case, the mask has all bits of a 32-bit integer set,
     * except the signed bit. The signed bit is the bit that determines
     * if the number is positive or negative. ANDing (&) with this mask
     * effectively sets the signed bit to 0, which means the number
     * will always be positive.
     */
    private int normalizeIndex(int hashCode) {
        return (hashCode & 0x7FFFFFFF) % capacity;
    }

    private EntryNode<K, V> bucketSeekEntry(K key) {
        int tableIndex = normalizeIndex(key.hashCode());
        EntryNode<K, V> bucket = table[tableIndex];
        if (bucket == null) return null;

        while (bucket != null) {
            if (bucket.key.equals(key)) {
                return bucket;
            }
            bucket = bucket.next;
        }

        return null;
    }

    private void checkKeyNotNull(K key) {
        if (key == null) throw new NullPointerException("Nullable keys is not allowed");
    }

    private void checkValueNotNull(V value) {
        if (value == null) throw new NullPointerException("Nullable values is not allowed");
    }
}
