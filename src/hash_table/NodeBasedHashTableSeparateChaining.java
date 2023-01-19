package hash_table;

import java.util.Iterator;

@SuppressWarnings("unchecked")
public class NodeBasedHashTableSeparateChaining<K, V> implements Hashable<K, V>, Iterable<V> {

    private static class Entry<K, V> {
        final int hash;
        K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
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
        public boolean equals(Entry<K, V> other) {
            if (hash != other.hash) return false;
            return key.equals(other.key);
        }
    }

    private static final int DEFAULT_CAPACITY = 3;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    private final double maxLoadFactor;
    private int capacity;
    private int threshold;
    private int count = 0;

    private Entry<K, V>[] table;


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
        table = (Entry<K, V>[]) new Entry[capacity];
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
        for (int i = 0; i < table.length; i++) {
            clearBucket(table[i]);
            table[i] = null;
        }
        count = 0;
    }

    private void clearBucket(Entry<K, V> head) {
        while (head != null) {
            Entry<K, V> next = head.next;
            head.next = null;
            head.value = null;
            head = next;
        }
    }

    @Override
    public boolean containsKey(K key) {
        checkKeyNotNull(key);
        int hash = key.hashCode();
        int index = normalizeIndex(hash);
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(V value) {
        checkValueNotNull(value);
        for (Entry<K, V> bucket : table) {
            for (Entry<K, V> e = bucket; e != null; e = e.next) {
                if (e.value.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        checkKeyNotNull(key);
        int hash = key.hashCode();
        int index = normalizeIndex(hash);
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.hash == hash && e.value.equals(key)) {
                return e.value;
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        checkValueNotNull(value);
        checkKeyNotNull(key);
        int hash = key.hashCode();
        int index = normalizeIndex(hash);
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.hash == hash && e.key.equals(key)) {
                V old = e.value;
                e.value = value;
                return old;
            }
        }

        addEntry(hash, index, key, value);
        return null;
    }

    private void addEntry(int hash, int index, K key, V value) {
        if (count >= threshold) {
            resizeTable(capacity * 2);
            index = normalizeIndex(hash);
        }
        Entry<K, V> e = table[index];
        table[index] = new Entry<>(key, value, e);
        count++;
    }

    private void resizeTable(int capacity) {
        this.capacity = capacity;
        threshold = (int) (maxLoadFactor * capacity);

        Entry<K, V>[] newTable = (Entry<K, V>[]) new Entry[capacity];

        for (int i = 0; i < table.length; i++) {
            for (Entry<K, V> e = table[i]; e != null; e = e.next) {
                int hashIndex = normalizeIndex(e.hash);
                e.next = newTable[hashIndex];
                newTable[hashIndex] = e;
            }
            table[i] = null;
        }
        table = newTable;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public Iterator<V> iterator() {
        return null;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < capacity; i++) {
            Entry<K, V> bucket = table[i];
            if (bucket != null) {
                sb.append(toString(bucket)).append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private String toString(Entry<K, V> bucket) {
        StringBuilder sb = new StringBuilder().append("[");
        Entry<K, V> trav = bucket;
        while (trav != null) {
            sb.append(trav);
            trav = trav.next;
            if (trav != null) {
                sb.append(", ");
            }
        }
        return sb.append("]").toString();
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
//         return (hashCode & 0x7FFFFFFF) % capacity;
        return (hashCode % 2) == 0 ? 1 : 2;
    }

    private void checkKeyNotNull(K key) {
        if (key == null) throw new NullPointerException("Nullable keys is not allowed");
    }

    private void checkValueNotNull(V value) {
        if (value == null) throw new NullPointerException("Nullable values is not allowed");
    }

    public static void main(String[] args) {
        NodeBasedHashTableSeparateChaining<Integer, Integer> custom = new NodeBasedHashTableSeparateChaining<>(4);

        for (int i = 0; i < 20; i++) {
            custom.put(i, i);
        }

        System.out.println(custom);

        System.out.println("Is empty: " + custom.isEmpty());

        custom.clear();

        System.out.println(custom);

        System.out.println("Is empty: " + custom.isEmpty());


    }
}
