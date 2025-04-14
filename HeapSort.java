import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class HeapSort<K extends Comparable<K>, V> {
    private Comparator<K> comp;

    public HeapSort() { this.comp = new DefaultComparator<>(); }
    public HeapSort(Comparator<K> comp) { this.comp = comp; }

    private int compare(Entry<K, V> a, Entry<K, V> b) { return comp.compare(a.getKey(), b.getKey()); }

    public List<Entry<K, V>> sort(List<Entry<K, V>> list) {
        List<Entry<K, V>> heap = new ArrayList<>(list);
        int n = heap.size();
        for (int i = parent(n - 1); i >= 0; i--) downHeap(heap, i, n);
        List<Entry<K, V>> sorted = new ArrayList<>();
        for (int i = n - 1; i >= 0; i--) {
            sorted.add(heap.get(0));
            swap(heap, 0, i);
            downHeap(heap, 0, i);
        }
        return sorted;
    }

    private void downHeap(List<Entry<K, V>> heap, int i, int size) {
        while (left(i) < size) {
            int child = left(i), right = right(i);
            if (right < size && compare(heap.get(right), heap.get(child)) < 0) child = right;
            if (compare(heap.get(child), heap.get(i)) >= 0) break;
            swap(heap, i, child); i = child;
        }
    }

    private void swap(List<Entry<K, V>> heap, int i, int j) {
        Entry<K, V> tmp = heap.get(i); heap.set(i, heap.get(j)); heap.set(j, tmp);
    }

    private int parent(int i) { return (i - 1) / 2; }
    private int left(int i) { return 2 * i + 1; }
    private int right(int i) { return 2 * i + 2; }
}