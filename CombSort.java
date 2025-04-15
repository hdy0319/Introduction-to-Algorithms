import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class CombSort<K extends Comparable<K>, V> {
    private Comparator<K> comp;

    public CombSort() { this.comp = new DefaultComparator<>(); }
    public CombSort(Comparator<K> comp) { this.comp = comp; }

    private int compare(Entry<K, V> a, Entry<K, V> b) { return comp.compare(a.getKey(), b.getKey()); }

    public List<Entry<K, V>> sort(List<Entry<K, V>> list) {
        List<Entry<K, V>> arr = new ArrayList<>(list);
        int n = arr.size();
        int gap = n;
        boolean swapped = true;

        while (gap > 1 || swapped) {
            gap = Math.max((int)(gap / 1.3), 1);
            swapped = false;
            for (int i = 0; i + gap < n; i++) {
                if (compare(arr.get(i), arr.get(i + gap)) > 0) {
                    swap(arr, i, i + gap);
                    swapped = true;
                }
            }
        }
        return arr;
    }

    private void swap(List<Entry<K, V>> arr, int i, int j) {
        Entry<K, V> tmp = arr.get(i); arr.set(i, arr.get(j)); arr.set(j, tmp);
    }
}
