import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class InsertionSort<K extends Comparable<K>, V> {
    private Comparator<K> comp;

    public InsertionSort() { this.comp = new DefaultComparator<>(); }
    public InsertionSort(Comparator<K> comp) { this.comp = comp; }

    private int compare(Entry<K, V> a, Entry<K, V> b) { return comp.compare(a.getKey(), b.getKey()); }

    public List<Entry<K, V>> sort(List<Entry<K, V>> list) {
        List<Entry<K, V>> arr = new ArrayList<>(list);
        for (int i = 1; i < arr.size(); i++) {
            Entry<K, V> cur = arr.get(i);
            int j = i;
            while (j > 0 && compare(cur, arr.get(j - 1)) < 0) {
                arr.set(j, arr.get(j - 1));
                j--;
            }
            arr.set(j, cur);
        }
        return arr;
    }
}