import java.util.*;

public class LibrarySort<K extends Comparable<K>, V> {
    private Comparator<K> comp;
    private static final double EPSILON = 1.5; // gap factor

    public LibrarySort() { this.comp = new DefaultComparator<>(); }
    public LibrarySort(Comparator<K> comp) { this.comp = comp; }

    private int compare(Entry<K, V> a, Entry<K, V> b) {
        return comp.compare(a.getKey(), b.getKey());
    }

    public List<Entry<K, V>> sort(List<Entry<K, V>> list) {
        int n = list.size();
        if (n <= 1) return new ArrayList<>(list);

        int size = (int) (EPSILON * n) + 1;
        Entry<K, V>[] arr = (Entry<K, V>[]) new Entry[size];
        int count = 0;

        for (Entry<K, V> x : list) {
            int pos = searchInsertPos(arr, x, count);
            insertWithGap(arr, x, pos);
            count++;
            if (count * EPSILON + 1 > arr.length)
                arr = rebalance(arr, count);
        }

        List<Entry<K, V>> result = new ArrayList<>();
        for (Entry<K, V> e : arr)
            if (e != null) result.add(e);
        return result;
    }

    private int searchInsertPos(Entry<K, V>[] arr, Entry<K, V> x, int count) {
        int lo = 0, hi = arr.length - 1, pos = 0;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (arr[mid] == null) {
                int left = mid - 1, right = mid + 1;
                while (left >= lo && arr[left] == null) left--;
                while (right <= hi && arr[right] == null) right++;
                if (left < lo && right > hi) break;
                if (left >= lo && (right > hi || compare(x, arr[left]) <= 0)) {
                    hi = left;
                } else {
                    lo = right;
                }
            } else {
                if (compare(x, arr[mid]) <= 0) {
                    hi = mid - 1;
                    pos = mid;
                } else {
                    lo = mid + 1;
                    pos = lo;
                }
            }
        }
        return pos;
    }

    private void insertWithGap(Entry<K, V>[] arr, Entry<K, V> x, int pos) {
        while (pos < arr.length && arr[pos] != null) pos++;
        arr[pos] = x;
    }

    private Entry<K, V>[] rebalance(Entry<K, V>[] old, int count) {
        int newSize = (int) (EPSILON * count) + 1;
        Entry<K, V>[] fresh = (Entry<K, V>[]) new Entry[newSize];
        int i = 0, gap = (int) Math.floor((double) newSize / count);

        for (Entry<K, V> e : old)
            if (e != null && i < fresh.length)
                fresh[i++] = e;
            else if (i < fresh.length)
                i += gap;

        return fresh;
    }
}