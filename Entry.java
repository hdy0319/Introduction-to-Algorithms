public interface Entry<K, V> {
    K getKey();
    V getValue();
}

public class PQEntry<K, V> implements Entry<K, V> {
    private K key;
    private V value;

    public PQEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() { return key; }
    public V getValue() { return value; }
    protected void setKey(K key) { this.key = key; }
    protected void setValue(V value) { this.value = value; }
}

public class DefaultComparator<K> implements Comparator<Entry<K, ?>> {
    public int compare(Entry<K, ?> a, Entry<K, ?> b) {
        return ((Comparable<K>) a.getKey()).compareTo(b.getKey());
    }
}