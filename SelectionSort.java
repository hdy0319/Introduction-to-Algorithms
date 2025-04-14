import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class SelectionSort<K extends Comparable<K>, V> {
    private Comparator<K> comp;

    public SelectionSort() { this.comp = new DefaultComparator<>(); }
    public SelectionSort(Comparator<K> comp) { this.comp = comp; }

    private int compare(Entry<K, V> a, Entry<K, V> b) { return comp.compare(a.getKey(), b.getKey()); }

    public List<Entry<K, V>> sort(List<Entry<K, V>> list) {
        List<Entry<K, V>> arr = new ArrayList<>(list);
        int n = arr.size();
        for (int i = 0; i < n - 1; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (compare(arr.get(j), arr.get(min)) < 0) min = j;
            }
            if (min != i) swap(arr, i, min);
        }
        return arr;
    }

    private void swap(List<Entry<K, V>> arr, int i, int j) {
        Entry<K, V> tmp = arr.get(i); arr.set(i, arr.get(j)); arr.set(j, tmp);
    }
}