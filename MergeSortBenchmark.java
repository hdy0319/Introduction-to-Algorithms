import java.util.*;
import java.io.*;
import java.nio.file.*;

public class MergeSortBenchmark {
    public static void main(String[] args) throws Exception {
        List<Integer> original = loadInput("data/input_1k.txt");
        int[] expected = loadAnswer("data/answer_1k.txt");

        // Test
        String status = (Arrays.equals(mergeSort(new ArrayList<>(original)), expected)) ? "Test Completed" : "Test Failed";
        System.out.printf(status + "\n\n");

        double totalTime = 0;

        for (int i = 1; i <= 10; i++) {
            List<Integer> input = new ArrayList<>(original);

            long start = System.nanoTime();
            int[] result = mergeSort(input);
            long end = System.nanoTime();

            double timeMs = (end - start) / 1_000_000.0;
            totalTime += timeMs;

            System.out.printf("Run %2d: %.3f ms\n", i, timeMs);
        }

        double avg = totalTime / 10.0;
        System.out.printf("\nAverage time: %.3f ms\n", avg);
    }

    static List<Integer> loadInput(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String[] tokens = br.readLine().trim().split(" ");
        br.close();
        List<Integer> list = new ArrayList<>();
        for (String t : tokens) list.add(Integer.parseInt(t));
        return list;
    }

    static int[] loadAnswer(String filename) throws IOException {
        String[] tokens = Files.readString(Path.of(filename)).trim().split(" ");
        int[] arr = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) arr[i] = Integer.parseInt(tokens[i]);
        return arr;
    }

    static int[] mergeSort(List<Integer> input) {
        List<Entry<Integer, String>> entries = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            entries.add(new PQEntry<>(input.get(i), "V" + i));
        }

        MergeSort<Integer, String> sorter = new MergeSort<>();
        List<Entry<Integer, String>> sorted = sorter.sort(entries);

        int[] result = new int[sorted.size()];
        for (int i = 0; i < sorted.size(); i++) {
            result[i] = sorted.get(i).getKey();
        }
        return result;
    }
}