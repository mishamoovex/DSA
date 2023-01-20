package lru_cache;

public interface Lru<K,V> {

    int size();

    boolean isEmpty();

    void clear();

    V get(K key);

    void put(K key, V value);

}
