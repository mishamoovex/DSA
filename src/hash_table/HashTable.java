package hash_table;

import java.util.List;

public interface HashTable<K, V> {
    //Returns the number of elements currently inside the hash-table
    int size();

    //Returns true/false depending on whether the hash-table is empty
    boolean isEmpty();

    //Clears all the contents of the hash-table
    void clear();

    boolean containsKey(K key);

    //Returns true/false depending on whether a key is in the hash table
    boolean hasKey(K key);

    V put(K key, V value);

    V add(K key, V value);

    V insert(K key, V vale);

    //Gets a key's values from the map and returns the value.
    //NOTE: returns null if the value is null AND also returns
    //null if the key doesn't exist
    V get(K key);

    //Removes a key form the map and returns the value.
    //NOTE: returns null if the value is null AND also returns
    //null if the key doesn't exist
    V remove(K key);

    //Returns the list of keys found withing the hash table
    List<K> keys();

    //Returns the list of values found withing the hash table
    List<V> values();
}
