import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class TournamentSort<K extends Comparable<K>, V> {
    private Comparator<K> comp;

    public TournamentSort() { this.comp = new DefaultComparator<>(); }
    public TournamentSort(Comparator<K> comp) { this.comp = comp; }

    private int compare(Entry<K, V> a, Entry<K, V> b) {
        return comp.compare(a.getKey(), b.getKey());
    }

    public List<Entry<K, V>> sort(List<Entry<K, V>> list) {
        List<Entry<K, V>> arr = new ArrayList<>(list);
        List<Entry<K, V>> result = new ArrayList<>();

        while (!arr.isEmpty()) {
            Entry<K, V> min = arr.get(0);
            int minIndex = 0;
            for (int i = 1; i < arr.size(); i++) {
                if (compare(arr.get(i), min) < 0) {
                    min = arr.get(i);
                    minIndex = i;
                }
            }
            result.add(min);
            arr.remove(minIndex); // Remove winner
        }

        return result;
    }
}