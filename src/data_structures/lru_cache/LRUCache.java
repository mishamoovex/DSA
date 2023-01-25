package data_structures.lru_cache;

@SuppressWarnings("unchecked")
class LRUCache<K, V> implements Lru<K, V> {

    private final int capacity;
    private int count = 0;

    private final Node<K, V>[] table;
    private Node<K, V> head;
    private Node<K, V> tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        table = (Node<K, V>[]) new Node[capacity];
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
            table[i] = null;
        }
        cleaList();
        count = 0;
    }

    private void cleaList() {
        Node<K, V> trav = head;
        while (trav != null) {
            Node<K, V> next = trav.next;
            trav.value = null;
            trav.next = null;
            trav = next;
        }
        head = null;
        tail = null;
    }

    @Override
    public V get(K key) {
        checkKey(key);
        int hash = key.hashCode();
        int index = normalizeIndex(hash);
        Node<K, V> node = table[index];
        if (node != null) {
            setRecentlyUsed(node);
            return node.value;
        } else {
            return null;
        }
    }

    @Override
    public void put(K key, V value) {
        checkKey(key);
        checkValue(value);

        int hash = key.hashCode();
        int index = normalizeIndex(hash);

        Node<K, V> node = table[index];

        if (node != null && node.key.equals(key)) {
            node.value = value;
            setRecentlyUsed(node);
        } else {
            add(index, key, value);
        }
    }

    private void add(int index, K key, V value) {
        if (count >= capacity) {
            removeLeastUsed();
            count--;
        }

        if (isEmpty() || capacity == 1) {
            table[index] = head = tail = new Node<>(key, value, null, null);
        } else {
            table[index] = head.prev = new Node<>(key, value, null, head);
            head = head.prev;
        }
        count++;
    }

    private void setRecentlyUsed(Node<K, V> node) {
        //If we need to change usage for head of if there is
        //only one item in the cache -> do nothing
        if (node.prev == null) {
            return;
        } else if (node.next == null) {
            //Reset tail
            node.prev.next = null;
            tail = node.prev;
        } else {
            //Remove item from the middle
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        //Reset head
        node.prev = null;
        node.next = head;
        head.prev = node;
        head = head.prev;
    }

    private void removeLeastUsed() {
        Node<K, V> t = tail;
        //Clean up slot in the hash table
        int hash = t.key.hashCode();
        int index = normalizeIndex(hash);
        table[index] = null;

        if (capacity != 1) {
            //Reset the tail of the data_structures.list
            tail = t.prev;
            tail.next = null;
            //Clean up data from the evicted tail
            t.value = null;
            t.prev = null;
        }
    }

    private int normalizeIndex(int hashCode) {
        return (hashCode & 0x7FFFFFFF) % capacity;
    }

    private void checkKey(K key) {
        if (key == null) throw new IllegalArgumentException("Nullable key isn't supported");
    }

    private void checkValue(V value) {
        if (value == null) throw new IllegalArgumentException("Nullable value isn't supported");
    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;

        public Node(K key, V value, Node<K, V> prev, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return key + "=>" + value;
        }
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        } else {
            StringBuilder sb = new StringBuilder().append("[");
            Node<K, V> trav = head;
            while (trav != null) {
                sb.append(trav);
                trav = trav.next;
                if (trav != null) {
                    sb.append(", ");
                }
            }
            return sb.append("]").toString();
        }
    }

    public static void main(String[] args) {
        Lru<String, String> cache = new LRUCache<>(1);

        for (int i = 0; i < 1; i++) {
            cache.put("Key" + i, "Value" + i);
        }

        System.out.println(cache);

        System.out.println("GET: " + cache.get("Key" + 0));

        System.out.println(cache);

        cache.put("Key" + 2, "Value" + 99);

        System.out.println(cache);

        cache.put("Key" + 7, "Value" + 7);

        System.out.println(cache);

        cache.clear();

        System.out.println(cache);
    }
}
