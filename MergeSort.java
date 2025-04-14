public class MergeSort<K, V> extends AbstractPriorityQueue<K, V> {
    public MergeSort() { super(); }
    public MergeSort(Comparator<K> comp) { super(comp); }

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

    public void insert(K key, V value) {
        throw new UnsupportedOperationException("insert not supported in MergeSort");
    }

    public Entry<K, V> min() {
        throw new UnsupportedOperationException("min not supported in MergeSort");
    }

    public Entry<K, V> removeMin() {
        throw new UnsupportedOperationException("removeMin not supported in MergeSort");
    }

    public int size() {
        throw new UnsupportedOperationException("size not supported in MergeSort");
    }
}

public class LinkedMergeSort<K, V> extends AbstractPriorityQueue<K, V> {
    public LinkedMergeSort() { super(); }
    public LinkedMergeSort(Comparator<K> comp) { super(comp); }

    public int[] sort(Entry<K, V>[] data) {
        int n = data.length;
        int[] link = new int[n];
        for (int i = 0; i < n; i++) link[i] = -1;

        boolean done = false;
        while (!done) {
            int head = -1, tail = -1;
            int i = 0;
            done = true;

            while (i < n) {
                int j = i;
                while (j < n && link[j] < 0) j++;

                if (j >= n) {
                    if (head == -1) return link;
                    link[tail] = i;
                    break;
                }

                int p = i, q = j;
                int next = q;

                i = q;
                while (i < n && link[i] < 0) i++;

                int merged = -1, mergedTail = -1;
                while (p < q && q < i) {
                    int cmp = compare(data[p], data[q]);
                    int selected;
                    if (cmp <= 0) { selected = p++; }
                    else { selected = q++; }

                    if (merged == -1) {
                        merged = selected;
                        mergedTail = selected;
                    } else {
                        link[mergedTail] = selected;
                        mergedTail = selected;
                    }
                }

                while (p < q) {
                    if (merged == -1) {
                        merged = p;
                        mergedTail = p++;
                    } else {
                        link[mergedTail] = p;
                        mergedTail = p++;
                    }
                }

                while (q < i) {
                    if (merged == -1) {
                        merged = q;
                        mergedTail = q++;
                    } else {
                        link[mergedTail] = q;
                        mergedTail = q++;
                    }
                }
                link[mergedTail] = -1;

                if (head == -1) head = merged;
                else link[tail] = merged;
                tail = mergedTail;
                done = false;
            }
            if (head != -1) link[tail] = -1;
        }

        return link;
    }

    public void insert(K key, V value) { throw new UnsupportedOperationException("insert not supported"); }

    public Entry<K, V> min() {
        throw new UnsupportedOperationException("min not supported");
    }

    public Entry<K, V> removeMin() {
        throw new UnsupportedOperationException("removeMin not supported");
    }

    public int size() {
        throw new UnsupportedOperationException("size not supported");
    }
}