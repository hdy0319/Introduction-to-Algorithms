public abstract class AbstractPriorityQueue<K, V> {
    protected Comparator<K> comp;

    protected AbstractPriorityQueue() { this(new DefaultComparator<K>()); }

    protected AbstractPriorityQueue(Comparator<K> c) { comp = c; }

    protected int compare(Entry<K, V> a, Entry<K, V> b) { return comp.compare(a.getKey(), b.getKey()); }

    protected void checkKey(K key) throws IllegalArgumentException {
        try { comp.compare(key, key); }
        catch (ClassCastException e) { throw new IllegalArgumentException("Incompatible key"); }
    }

    public boolean isEmpty() { return size() == 0; }

    public abstract int size();
    public abstract Entry<K, V> min();
    public abstract Entry<K, V> removeMin();
    public abstract void insert(K key, V value);
}