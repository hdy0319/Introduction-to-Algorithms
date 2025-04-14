import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.io.BufferedReader;
import java.io.FileReader;

public class MergeSortTest {
    public static void main(String[] args) throws Exception {
        List<Integer> input = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("data/input_1k.txt"));
        String[] tokens = br.readLine().split(" ");
        for (String t : tokens) input.add(Integer.parseInt(t));
        br.close();

        List<Entry<Integer, String>> entries = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            entries.add(new PQEntry<>(input.get(i), "V" + i));
        }

        MergeSort<Integer, String> sorter = new MergeSort<>();

        long start = System.nanoTime();
        List<Entry<Integer, String>> sorted = sorter.sort(entries);
        long end = System.nanoTime();
            
        System.out.println("Sorted Result:");
        for (Entry<Integer, String> e : sorted) {
            System.out.print(e.getKey() + " ");
        }
        System.out.println();
        
        System.out.printf("⏱ Elapsed time: %.3f ms\n", (end - start) / 1_000_000.0);

    }
}