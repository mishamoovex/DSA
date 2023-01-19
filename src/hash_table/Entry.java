package hash_table;

public class Entry<K, V> {

    int hash;
    K key;
    V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
        hash = key.hashCode();
    }

    //We are not overriding the Object equals method
    //no casting is required with this method
    public boolean equals(Entry<K, V> other) {
        if (hash != other.hash) return false;
        return key.equals(other.key);
    }

    @Override
    public String toString() {
        return key + " => " + value;
    }
}
