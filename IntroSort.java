import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class IntroSort<K extends Comparable<K>, V> {
    private Comparator<K> comp;

    public IntroSort() { this.comp = new DefaultComparator<>(); }
    public IntroSort(Comparator<K> comp) { this.comp = comp; }

    private int compare(Entry<K, V> a, Entry<K, V> b) {
        return comp.compare(a.getKey(), b.getKey());
    }

    public List<Entry<K, V>> sort(List<Entry<K, V>> list) {
        List<Entry<K, V>> arr = new ArrayList<>(list);
        int depth = 2 * (int)(Math.log(arr.size()) / Math.log(2));
        introsort(arr, 0, arr.size() - 1, depth);
        return arr;
    }

    private void introsort(List<Entry<K, V>> arr, int low, int high, int depth) {
        if (high - low < 16) {
            insertionSort(arr, low, high);
        } else if (depth == 0) {
            heapSort(arr, low, high);
        } else {
            int p = partition(arr, low, high);
            introsort(arr, low, p - 1, depth - 1);
            introsort(arr, p + 1, high, depth - 1);
        }
    }

    private void insertionSort(List<Entry<K, V>> arr, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            Entry<K, V> key = arr.get(i);
            int j = i - 1;
            while (j >= low && compare(key, arr.get(j)) < 0) {
                arr.set(j + 1, arr.get(j));
                j--;
            }
            arr.set(j + 1, key);
        }
    }

    private void heapSort(List<Entry<K, V>> arr, int low, int high) {
        int n = high - low + 1;
        for (int i = low + n / 2 - 1; i >= low; i--)
            downHeap(arr, i, high);
        for (int i = high; i > low; i--) {
            swap(arr, low, i);
            downHeap(arr, low, i - 1);
        }
    }

    private void downHeap(List<Entry<K, V>> arr, int i, int end) {
        while (left(i) <= end) {
            int child = left(i);
            if (child + 1 <= end && compare(arr.get(child + 1), arr.get(child)) > 0) child++;
            if (compare(arr.get(i), arr.get(child)) >= 0) break;
            swap(arr, i, child); i = child;
        }
    }

    private int left(int i) { return 2 * i + 1; }

    private int partition(List<Entry<K, V>> arr, int low, int high) {
        Entry<K, V> pivot = arr.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (compare(arr.get(j), pivot) <= 0)
                swap(arr, ++i, j);
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private void swap(List<Entry<K, V>> arr, int i, int j) {
        Entry<K, V> tmp = arr.get(i); arr.set(i, arr.get(j)); arr.set(j, tmp);
    }
}