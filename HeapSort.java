import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class HeapPriorityQueue<K, V> extends AbstractPriorityQueue<K, V> {
    protected ArrayList<Entry<K, V>> heap = new ArrayList<>();
    public HeapPriorityQueue() { super(); }
    public HeapPriorityQueue(Comparator<K> comp) { super(comp); }
    public HeapPriorityQueue(List<Entry<K,V>> entries) {
        super();
        heap.addAll(entries);
        for (int j = parent(heap.size() - 1); j >= 0; j--)
            downHeap(j);
    }

    protected int parent(int j) { return (j - 1) / 2; }
    protected int left(int j) { return 2 * j + 1; }
    protected int right(int j) { return 2 * j + 2; }

    protected boolean hasLeft(int j) { return left(j) < heap.size(); }
    protected boolean hasRight(int j) { return right(j) < heap.size(); }
    protected boolean hasParent(int j) { return j > 0; }

    protected void swap(int i, int j) {
        Entry<K, V> temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    protected void upHeap(int j) {
        while (j > 0) {
            int parent = parent(j);
            if (compare(heap.get(j), heap.get(parent)) >= 0) break;
            swap(j, parent);
            j = parent;
        }
    }

    protected void downHeap(int j) {
        while (hasLeft(j)) {
            int left = left(j);
            int smallChild = left;
            if (hasRight(j)) {
                int right = right(j);
                if (compare(heap.get(left), heap.get(right)) > 0)
                    smallChild = right;
            }
            if (compare(heap.get(smallChild), heap.get(j)) >= 0) break;
            swap(j, smallChild);
            j = smallChild;
        }
    }

    public int size() { return heap.size(); }

    public Entry<K, V> min() {
        if (heap.isEmpty()) return null;
        return heap.get(0);
    }

    public Entry<K, V> removeMin() {
        if (heap.isEmpty()) return null;
        Entry<K, V> answer = heap.get(0);
        swap(0, heap.size() - 1);
        heap.remove(heap.size() - 1);
        downHeap(0);
        return answer;
    }

    public void insert(K key, V value) throws IllegalArgumentException {
        checkKey(key);
        Entry<K, V> newest = new PQEntry<>(key, value);
        heap.add(newest);
        upHeap(heap.size() - 1);
    }
}

public List<Entry<K, V>> heapSort(List<Entry<K, V>> entries) {
    HeapPriorityQueue<K, V> pq = new HeapPriorityQueue<>(entries);
    List<Entry<K, V>> sorted = new ArrayList<>();
    while (!pq.isEmpty()) {
        sorted.add(pq.removeMin());
    }
    return sorted;
}