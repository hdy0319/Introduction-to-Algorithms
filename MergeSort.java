import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class MergeSort<K, V> {
    private Comparator<K> comp;

    public MergeSort() { this(new DefaultComparator<>()); }

    public MergeSort(Comparator<K> comp) { this.comp = comp; }

    private int compare(Entry<K, V> a, Entry<K, V> b) { return comp.compare(a.getKey(), b.getKey()); }

    public List<Entry<K, V>> sort(List<Entry<K, V>> list) {
        if (list.size() <= 1) return list;

        int mid = list.size() / 2;
        List<Entry<K, V>> left = sort(new ArrayList<>(list.subList(0, mid)));
        List<Entry<K, V>> right = sort(new ArrayList<>(list.subList(mid, list.size())));
        return merge(left, right);
    }

    private List<Entry<K, V>> merge(List<Entry<K, V>> A, List<Entry<K, V>> B) {
        List<Entry<K, V>> S = new ArrayList<>();
        int i = 0, j = 0;
        while (i < A.size() && j < B.size()) {
            int cmp = compare(A.get(i), B.get(j));
            if (cmp < 0) S.add(A.get(i++));
            else if (cmp > 0) S.add(B.get(j++));
            else {
                S.add(A.get(i++));
                S.add(B.get(j++));
            }
        }
        while (i < A.size()) S.add(A.get(i++));
        while (j < B.size()) S.add(B.get(j++));
        return S;
    }
}