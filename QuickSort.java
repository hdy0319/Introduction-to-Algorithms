import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class QuickSort<K extends Comparable<K>, V> {
    private Comparator<K> comp;

    public QuickSort() { this.comp = new DefaultComparator<>(); }
    public QuickSort(Comparator<K> comp) { this.comp = comp; }

    private int compare(Entry<K, V> a, Entry<K, V> b) { return comp.compare(a.getKey(), b.getKey()); }

    public List<Entry<K, V>> sort(List<Entry<K, V>> list) {
        List<Entry<K, V>> arr = new ArrayList<>(list);
        quicksort(arr, 0, arr.size() - 1);
        return arr;
    }

    private void quicksort(List<Entry<K, V>> arr, int low, int high) {
        if (low < high) {
            int p = partition(arr, low, high);
            quicksort(arr, low, p - 1);
            quicksort(arr, p + 1, high);
        }
    }

    private int partition(List<Entry<K, V>> arr, int low, int high) {
        Entry<K, V> pivot = arr.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (compare(arr.get(j), pivot) <= 0) swap(arr, ++i, j);
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private void swap(List<Entry<K, V>> arr, int i, int j) {
        Entry<K, V> tmp = arr.get(i); arr.set(i, arr.get(j)); arr.set(j, tmp);
    }
}