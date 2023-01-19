package hash_table;

import list.LinkedList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unchecked")
public class HashTableSeparateChaining<K, V> implements Iterable<V> {

    private static final int DEFAULT_CAPACITY = 3;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    //If the load factor goes above this value, then we need to resize the table
    private final double maxLoadFactor;
    //Table size;
    private int capacity;
    //Capacity times load factor, tels about the time to resize
    private int threshold;
    //How many items currently on the table
    private int size;

    private LinkedList<Entry<K, V>>[] table;

    public HashTableSeparateChaining() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public HashTableSeparateChaining(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    public HashTableSeparateChaining(int capacity, double maxLoadFactor) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Illegal capacity");
        }
        if (maxLoadFactor <= 0 || Double.isNaN(maxLoadFactor) || Double.isInfinite(maxLoadFactor)) {
            throw new IllegalArgumentException("Illegal maxLoadFactor");
        }
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = Math.max(DEFAULT_CAPACITY, capacity);
        threshold = (int) (this.capacity * maxLoadFactor);
        table = new LinkedList[this.capacity];
    }

    //Returns the number of elements currently inside the hash-table
    public int size() {
        return size;
    }

    //Returns true/false depending on whether the hash-table is empty
    public boolean isEmpty() {
        return size == 0;
    }

    //Clears all the contents of the hash-table
    public void clear() {
        for (int i = 0, len = table.length; i < len; i++) {
            table[i] = null;
        }
        size = 0;
    }

    public boolean containsKey(K key) {
        return hasKey(key);
    }

    //Returns true/false depending on whether a key is in the hash table
    public boolean hasKey(K key) {
        int bucketIndex = normalizeIndex(key.hashCode());
        return bucketSeekEntry(bucketIndex, key) != null;
    }

    public V put(K key, V value) {
        return insert(key, value);
    }

    public V add(K key, V value) {
        return insert(key, value);
    }

    public V insert(K key, V vale) {
        if (key == null) {
            throw new IllegalArgumentException("Null key");
        }
        Entry<K, V> newEntry = new Entry<>(key, vale);
        int bucketIndex = normalizeIndex(newEntry.hash);
        return bucketInsertEntry(bucketIndex, newEntry);
    }

    //Gets a key's values from the map and returns the value.
    //NOTE: returns null if the value is null AND also returns
    //null if the key doesn't exist
    public V get(K key) {
        if (key == null) {
            return null;
        } else {
            int buckedIndex = normalizeIndex(key.hashCode());
            Entry<K, V> entry = bucketSeekEntry(buckedIndex, key);
            return entry != null ? entry.value : null;
        }
    }

    //Removes a key form the map and returns the value.
    //NOTE: returns null if the value is null AND also returns
    //null if the key doesn't exist
    public V remove(K key) {
        if (key == null) {
            return null;
        } else {
            int bucketIndex = normalizeIndex(key.hashCode());
            return bucketRemoveEntry(bucketIndex, key);
        }
    }

    //Returns the list of keys found withing the hash table
    public List<K> keys() {
        List<K> keys = new ArrayList<>();
        for (LinkedList<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    keys.add(entry.key);
                }
            }
        }
        return keys;
    }

    //Returns the list of values found withing the hash table
    public List<V> values() {
        List<V> values = new ArrayList<>();
        for (LinkedList<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    values.add(entry.value);
                }
            }
        }
        return values;
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
    private int normalizeIndex(int keyHash) {
        return (keyHash & 0x7FFFFFFF) % capacity;
    }

    //Removes an entry from the given bucked if it exists
    private V bucketRemoveEntry(int buckedIndex, K key) {
        Entry<K, V> entry = bucketSeekEntry(buckedIndex, key);
        if (entry != null) {
            LinkedList<Entry<K, V>> bucket = table[buckedIndex];
            bucket.remove(entry);
            size--;
            return entry.value;
        } else {
            return null;
        }
    }

    private V bucketInsertEntry(int buckedIndex, Entry<K, V> entry) {
        LinkedList<Entry<K, V>> bucket = table[buckedIndex];
        if (bucket == null) {
            table[buckedIndex] = bucket = new LinkedList<>();
        }
        Entry<K, V> existentEntry = bucketSeekEntry(buckedIndex, entry.key);
        //Updated the key value
        if (existentEntry != null) {
            V oldVal = existentEntry.value;
            existentEntry.value = entry.value;
            return oldVal;
        } else {
            bucket.add(entry);
            if (++size >= threshold) resizeTable();
            //Use null to indicate that there was no previous entry
            return null;
        }
    }

    private Entry<K, V> bucketSeekEntry(int buckedIndex, K key) {
        if (key == null) return null;
        LinkedList<Entry<K, V>> bucket = table[buckedIndex];
        if (bucket == null) return null;
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                return entry;
            }
        }
        return null;
    }

    private void resizeTable() {
        //Update the capacity and the threshold
        capacity *= 2;
        threshold = (int) (capacity * maxLoadFactor);
        //Create a table with updated capacity
        LinkedList<Entry<K, V>>[] newTable = new LinkedList[capacity];
        //Iterate through each element in the old table
        for (int i = 0; i < table.length; i++) {
            //Check if the bucket by the given index exists
            if (table[i] != null) {
                //If the bucket exists -> copy its data to a new bucked
                for (Entry<K, V> entry : table[i]) {
                    //Calculates the index to get a bucked from the newTable
                    //if its exists
                    int buckedIndex = normalizeIndex(entry.hash);
                    LinkedList<Entry<K, V>> bucket = newTable[buckedIndex];
                    //If there are no item with the given hash-code in the newTable
                    //we should create a new bucket
                    if (bucket == null) {
                        newTable[buckedIndex] = bucket = new LinkedList<>();
                    }
                    //Add the entry from the old table to the new table
                    bucket.add(entry);
                }
                //To avoid memory leaks clear the data from the old bucked
                //and remove it form the old table
                table[i].clear();
                table[i] = null;
            }
        }
        table = newTable;
    }

    @Override
    public Iterator<V> iterator() {
        return null;
    }
}
