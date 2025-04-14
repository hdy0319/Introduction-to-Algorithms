import java.util.Comparator;

public class DefaultComparator<K extends Comparable<K>> implements Comparator<K> {
    @SuppressWarnings("unchecked")
    public int compare(K a, K b) {
        return ((Comparable<K>) a).compareTo(b);
    }
}