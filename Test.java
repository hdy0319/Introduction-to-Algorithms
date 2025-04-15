import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.function.Function;

public class Test {
    public static void main(String[] args) throws Exception {
        String algo = args[0];
        String size = args[1];
    
        String inputPath = "data/answer_" + size + ".txt";
        String answerPath = "data/answer_" + size + ".txt";
    
        List<Integer> original = loadInput(inputPath);
        int[] expected = loadAnswer(answerPath);
    
        PrintWriter out = new PrintWriter(new FileWriter("out/" + algo + ".txt", true));
        PrintWriter timecsv = new PrintWriter(new FileWriter("time.csv", true));
        PrintWriter memcsv = new PrintWriter(new FileWriter("memory.csv", true));
    
        System.out.printf("\n\nLoaded input size: " + original.size());
        out.printf("\n\nLoaded input size: " + original.size());
    
        Map<String, Function<List<Integer>, int[]>> algos = Map.ofEntries(
            Map.entry("merge", Test::mergeSort),
            Map.entry("heap", Test::heapSort),
            Map.entry("bubble", Test::bubbleSort),
            Map.entry("insertion", Test::insertionSort),
            Map.entry("selection", Test::selectionSort),
            Map.entry("quick", Test::quickSort),
            Map.entry("library", Test::librarySort),
            Map.entry("timsort", Test::timSort),
            Map.entry("cocktail", Test::cocktailSort),
            Map.entry("comb", Test::combSort),
            Map.entry("tournament", Test::tournamentSort),
            Map.entry("intro", Test::introSort)
        );
    
        Function<List<Integer>, int[]> sortFunc = algos.get(algo);
    
        // warm up
        sortFunc.apply(new ArrayList<>(original));
        sortFunc.apply(new ArrayList<>(original));
    
        // Correctness Test (키 값만 검사)
        String status = (Arrays.equals(sortFunc.apply(new ArrayList<>(original)), expected))
                        ? "Test Completed" : "Test Failed";
        System.out.printf("\n" + status + "\n\n");
        out.printf("\n" + status + "\n\n");
        timecsv.printf("\n%s,%s", algo, size);
        memcsv.printf("\n%s,%s", algo, size);
    
        double totalTime = 0;
        long totalMemory = 0;
    
        for (int i = 1; i <= 10; i++) {
            List<Integer> input = new ArrayList<>(original);

            Runtime runtime = Runtime.getRuntime();
            runtime.gc();
            long beforeMem = runtime.totalMemory() - runtime.freeMemory();
    
            long start = System.nanoTime();
            int[] result = sortFunc.apply(input);
            long end = System.nanoTime();

            long afterMem = runtime.totalMemory() - runtime.freeMemory();
            long usedMem = afterMem - beforeMem;  
            totalMemory += usedMem;
    
            double timeMs = (end - start) / 1_000_000.0;
            totalTime += timeMs;
    
            String msg = String.format("Run %2d: %.3f ms / %.3f MB", i, timeMs, (usedMem / (1024.0 * 1024.0)));
            timecsv.printf("%.3f,", timeMs);
            memcsv.printf("%.3f,", usedMem / (1024.0 * 1024.0));
            System.out.println(msg);
            out.println(msg);
        }
    
        double avg = totalTime / 10.0;
        double avgMem = totalMemory / 10.0 / (1024.0 * 1024.0);

        String msg = String.format("\nAverage : %.3f ms / %.3f MB\n\n", avg, avgMem);
        System.out.printf(msg);
        out.printf(msg);
//        timecsv.printf(String.format(".3f", avg));
//        memcsv.printf(String.format(".3f", avgMem));
        
        // stability test
        Map<String, Function<List<Integer>, List<Entry<Integer, String>>>> algoEntries = Map.ofEntries(
            Map.entry("merge", Test::mergeSortEntries),
            Map.entry("heap", Test::heapSortEntries),
            Map.entry("bubble", Test::bubbleSortEntries),
            Map.entry("insertion", Test::insertionSortEntries),
            Map.entry("selection", Test::selectionSortEntries),
            Map.entry("quick", Test::quickSortEntries),
            Map.entry("library", Test::librarySortEntries),
            Map.entry("timsort", Test::timSortEntries),
            Map.entry("cocktail", Test::cocktailSortEntries),
            Map.entry("comb", Test::combSortEntries),
            Map.entry("tournament", Test::tournamentSortEntries),
            Map.entry("intro", Test::introSortEntries)
        );
    
        List<Entry<Integer, String>> sortedEntries = algoEntries.get(algo).apply(new ArrayList<>(original));
        boolean stable = checkStability(sortedEntries);
        String stabilityStatus = stable ? "Stability Check: Passed" : "Stability Check: Failed";
        System.out.println(stabilityStatus);
        out.println(stabilityStatus);
        
        out.close();
        timecsv.close();
        memcsv.close();
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

    static int[] heapSort(List<Integer> input) {
        List<Entry<Integer, String>> entries = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            entries.add(new PQEntry<>(input.get(i), "V" + i));
        }
    
        HeapSort<Integer, String> sorter = new HeapSort<>();
        List<Entry<Integer, String>> sorted = sorter.sort(entries);
    
        int[] result = new int[sorted.size()];
        for (int i = 0; i < sorted.size(); i++) {
            result[i] = sorted.get(i).getKey();
        }
        return result;
    }

    static int[] bubbleSort(List<Integer> input) {
        List<Entry<Integer, String>> entries = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            entries.add(new PQEntry<>(input.get(i), "V" + i));
        }
    
        BubbleSort<Integer, String> sorter = new BubbleSort<>();
        List<Entry<Integer, String>> sorted = sorter.sort(entries);
    
        int[] result = new int[sorted.size()];
        for (int i = 0; i < sorted.size(); i++) {
            result[i] = sorted.get(i).getKey();
        }
        return result;
    }

    static int[] insertionSort(List<Integer> input) {
        List<Entry<Integer, String>> entries = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            entries.add(new PQEntry<>(input.get(i), "V" + i));
        }
    
        InsertionSort<Integer, String> sorter = new InsertionSort<>();
        List<Entry<Integer, String>> sorted = sorter.sort(entries);
    
        int[] result = new int[sorted.size()];
        for (int i = 0; i < sorted.size(); i++) {
            result[i] = sorted.get(i).getKey();
        }
        return result;
    }

    static int[] selectionSort(List<Integer> input) {
        List<Entry<Integer, String>> entries = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            entries.add(new PQEntry<>(input.get(i), "V" + i));
        }
    
        SelectionSort<Integer, String> sorter = new SelectionSort<>();
        List<Entry<Integer, String>> sorted = sorter.sort(entries);
    
        int[] result = new int[sorted.size()];
        for (int i = 0; i < sorted.size(); i++) {
            result[i] = sorted.get(i).getKey();
        }
        return result;
    }    

    static int[] quickSort(List<Integer> input) {
        List<Entry<Integer, String>> entries = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            entries.add(new PQEntry<>(input.get(i), "V" + i));
        }
    
        QuickSort<Integer, String> sorter = new QuickSort<>();
        List<Entry<Integer, String>> sorted = sorter.sort(entries);
    
        int[] result = new int[sorted.size()];
        for (int i = 0; i < sorted.size(); i++) {
            result[i] = sorted.get(i).getKey();
        }
        return result;
    }
    
    static int[] librarySort(List<Integer> input) {
        List<Entry<Integer, String>> entries = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            entries.add(new PQEntry<>(input.get(i), "V" + i));
        }
    
        LibrarySort<Integer, String> sorter = new LibrarySort<>();
        List<Entry<Integer, String>> sorted = sorter.sort(entries);
    
        int[] result = new int[sorted.size()];
        for (int i = 0; i < sorted.size(); i++) {
            result[i] = sorted.get(i).getKey();
        }
        return result;
    }
    
    static int[] timSort(List<Integer> input) {
        List<Entry<Integer, String>> entries = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            entries.add(new PQEntry<>(input.get(i), "V" + i));
        }
    
        TimSort<Integer, String> sorter = new TimSort<>();
        List<Entry<Integer, String>> sorted = sorter.sort(entries);
    
        int[] result = new int[sorted.size()];
        for (int i = 0; i < sorted.size(); i++) {
            result[i] = sorted.get(i).getKey();
        }
        return result;
    }
    
    static int[] cocktailSort(List<Integer> input) {
        List<Entry<Integer, String>> entries = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            entries.add(new PQEntry<>(input.get(i), "V" + i));
        }
    
        CocktailShakerSort<Integer, String> sorter = new CocktailShakerSort<>();
        List<Entry<Integer, String>> sorted = sorter.sort(entries);
    
        int[] result = new int[sorted.size()];
        for (int i = 0; i < sorted.size(); i++) {
            result[i] = sorted.get(i).getKey();
        }
        return result;
    }
    
    static int[] combSort(List<Integer> input) {
        List<Entry<Integer, String>> entries = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            entries.add(new PQEntry<>(input.get(i), "V" + i));
        }
    
        CombSort<Integer, String> sorter = new CombSort<>();
        List<Entry<Integer, String>> sorted = sorter.sort(entries);
    
        int[] result = new int[sorted.size()];
        for (int i = 0; i < sorted.size(); i++) {
            result[i] = sorted.get(i).getKey();
        }
        return result;
    }

    static int[] tournamentSort(List<Integer> input) {
        List<Entry<Integer, String>> entries = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            entries.add(new PQEntry<>(input.get(i), "V" + i));
        }
    
        TournamentSort<Integer, String> sorter = new TournamentSort<>();
        List<Entry<Integer, String>> sorted = sorter.sort(entries);
    
        int[] result = new int[sorted.size()];
        for (int i = 0; i < sorted.size(); i++) {
            result[i] = sorted.get(i).getKey();
        }
        return result;
    }

    static int[] introSort(List<Integer> input) {
        List<Entry<Integer, String>> entries = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            entries.add(new PQEntry<>(input.get(i), "V" + i));
        }
    
        IntroSort<Integer, String> sorter = new IntroSort<>();
        List<Entry<Integer, String>> sorted = sorter.sort(entries);
    
        int[] result = new int[sorted.size()];
        for (int i = 0; i < sorted.size(); i++) {
            result[i] = sorted.get(i).getKey();
        }
        return result;
    }    
    
    // methods for stability test
    static List<Entry<Integer, String>> mergeSortEntries(List<Integer> input) {
        List<Entry<Integer, String>> entries = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            entries.add(new PQEntry<>(input.get(i), "V" + i));
        }
        MergeSort<Integer, String> sorter = new MergeSort<>();
        return sorter.sort(entries);
    }
    
    static List<Entry<Integer, String>> heapSortEntries(List<Integer> input) {
        List<Entry<Integer, String>> entries = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            entries.add(new PQEntry<>(input.get(i), "V" + i));
        }
        HeapSort<Integer, String> sorter = new HeapSort<>();
        return sorter.sort(entries);
    }
    
    static List<Entry<Integer, String>> bubbleSortEntries(List<Integer> input) {
        List<Entry<Integer, String>> entries = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            entries.add(new PQEntry<>(input.get(i), "V" + i));
        }
        BubbleSort<Integer, String> sorter = new BubbleSort<>();
        return sorter.sort(entries);
    }
    
    static List<Entry<Integer, String>> insertionSortEntries(List<Integer> input) {
        List<Entry<Integer, String>> entries = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            entries.add(new PQEntry<>(input.get(i), "V" + i));
        }
        InsertionSort<Integer, String> sorter = new InsertionSort<>();
        return sorter.sort(entries);
    }
    
    static List<Entry<Integer, String>> selectionSortEntries(List<Integer> input) {
        List<Entry<Integer, String>> entries = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            entries.add(new PQEntry<>(input.get(i), "V" + i));
        }
        SelectionSort<Integer, String> sorter = new SelectionSort<>();
        return sorter.sort(entries);
    }
    
    static List<Entry<Integer, String>> quickSortEntries(List<Integer> input) {
        List<Entry<Integer, String>> entries = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            entries.add(new PQEntry<>(input.get(i), "V" + i));
        }
        QuickSort<Integer, String> sorter = new QuickSort<>();
        return sorter.sort(entries);
    }
    
    static List<Entry<Integer, String>> librarySortEntries(List<Integer> input) {
        List<Entry<Integer, String>> entries = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            entries.add(new PQEntry<>(input.get(i), "V" + i));
        }
        LibrarySort<Integer, String> sorter = new LibrarySort<>();
        return sorter.sort(entries);
    }
    
    static List<Entry<Integer, String>> timSortEntries(List<Integer> input) {
        List<Entry<Integer, String>> entries = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            entries.add(new PQEntry<>(input.get(i), "V" + i));
        }
        TimSort<Integer, String> sorter = new TimSort<>();
        return sorter.sort(entries);
    }
    
    static List<Entry<Integer, String>> cocktailSortEntries(List<Integer> input) {
        List<Entry<Integer, String>> entries = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            entries.add(new PQEntry<>(input.get(i), "V" + i));
        }
        CocktailShakerSort<Integer, String> sorter = new CocktailShakerSort<>();
        return sorter.sort(entries);
    }
    
    static List<Entry<Integer, String>> combSortEntries(List<Integer> input) {
        List<Entry<Integer, String>> entries = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            entries.add(new PQEntry<>(input.get(i), "V" + i));
        }
        CombSort<Integer, String> sorter = new CombSort<>();
        return sorter.sort(entries);
    }
    
    static List<Entry<Integer, String>> tournamentSortEntries(List<Integer> input) {
        List<Entry<Integer, String>> entries = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            entries.add(new PQEntry<>(input.get(i), "V" + i));
        }
        TournamentSort<Integer, String> sorter = new TournamentSort<>();
        return sorter.sort(entries);
    }
    
    static List<Entry<Integer, String>> introSortEntries(List<Integer> input) {
        List<Entry<Integer, String>> entries = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            entries.add(new PQEntry<>(input.get(i), "V" + i));
        }
        IntroSort<Integer, String> sorter = new IntroSort<>();
        return sorter.sort(entries);
    }
    
    // stability check
    static boolean checkStability(List<Entry<Integer, String>> sorted) {
        for (int i = 0; i < sorted.size() - 1; i++) {
            if(sorted.get(i).getKey().equals(sorted.get(i+1).getKey())) {
                int index1 = extractIndex(sorted.get(i).getValue());
                int index2 = extractIndex(sorted.get(i+1).getValue());
                if(index1 > index2) {
                    return false;
                }
            }
        }
        return true;
    }
    
    static int extractIndex(String s) {
        return Integer.parseInt(s.substring(1));
    }
}