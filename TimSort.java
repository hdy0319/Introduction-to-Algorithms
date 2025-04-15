import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class TimSort<K extends Comparable<K>, V> {
    private Comparator<K> comp;
    private static final int RUN = 32;

    public TimSort() { this.comp = new DefaultComparator<>(); }
    public TimSort(Comparator<K> comp) { this.comp = comp; }

    private int compare(Entry<K, V> a, Entry<K, V> b) { return comp.compare(a.getKey(), b.getKey()); }

    public List<Entry<K, V>> sort(List<Entry<K, V>> list) {
        List<Entry<K, V>> arr = new ArrayList<>(list);
        int n = arr.size();

        for (int i = 0; i < n; i += RUN)
            insertionSort(arr, i, Math.min((i + RUN - 1), (n - 1)));

        for (int size = RUN; size < n; size *= 2) {
            for (int left = 0; left < n; left += 2 * size) {
                int mid = Math.min(n - 1, left + size - 1);
                int right = Math.min((left + 2 * size - 1), (n - 1));
                if (mid < right)
                    merge(arr, left, mid, right);
            }
        }

        return arr;
    }

    private void insertionSort(List<Entry<K, V>> arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            Entry<K, V> temp = arr.get(i);
            int j = i - 1;
            while (j >= left && compare(temp, arr.get(j)) < 0) {
                arr.set(j + 1, arr.get(j));
                j--;
            }
            arr.set(j + 1, temp);
        }
    }

    private void merge(List<Entry<K, V>> arr, int l, int m, int r) {
        List<Entry<K, V>> left = new ArrayList<>(arr.subList(l, m + 1));
        List<Entry<K, V>> right = new ArrayList<>(arr.subList(m + 1, r + 1));

        int i = 0, j = 0, k = l;
        while (i < left.size() && j < right.size()) {
            if (compare(left.get(i), right.get(j)) <= 0)
                arr.set(k++, left.get(i++));
            else
                arr.set(k++, right.get(j++));
        }
        while (i < left.size()) arr.set(k++, left.get(i++));
        while (j < right.size()) arr.set(k++, right.get(j++));
    }
}
