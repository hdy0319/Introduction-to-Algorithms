import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class CocktailShakerSort<K extends Comparable<K>, V> {
    private Comparator<K> comp;

    public CocktailShakerSort() { this.comp = new DefaultComparator<>(); }
    public CocktailShakerSort(Comparator<K> comp) { this.comp = comp; }

    private int compare(Entry<K, V> a, Entry<K, V> b) { return comp.compare(a.getKey(), b.getKey()); }

    public List<Entry<K, V>> sort(List<Entry<K, V>> list) {
        List<Entry<K, V>> arr = new ArrayList<>(list);
        int n = arr.size();
        boolean swapped = true;
        int start = 0, end = n - 1;

        while (swapped) {
            swapped = false;
            for (int i = start; i < end; i++) {
                if (compare(arr.get(i), arr.get(i + 1)) > 0) {
                    swap(arr, i, i + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;
            swapped = false;
            end--;
            for (int i = end; i > start; i--) {
                if (compare(arr.get(i - 1), arr.get(i)) > 0) {
                    swap(arr, i - 1, i);
                    swapped = true;
                }
            }
            start++;
        }
        return arr;
    }

    private void swap(List<Entry<K, V>> arr, int i, int j) {
        Entry<K, V> tmp = arr.get(i); arr.set(i, arr.get(j)); arr.set(j, tmp);
    }
}