import java.util.*;
import java.io.*;

public class SequenceGenerator {
    public static void main(String[] args) throws IOException {
        int size = 1000000;
        String fileName = "input_1m_1.txt";

        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            data.add(i);
        }

        Collections.shuffle(data, new Random());

        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        for (int i = 0; i < size; i++) {
            bw.write(data.get(i).toString());
            if (i != size - 1) bw.write(" ");
        }
        bw.newLine();
        bw.close();

        System.out.println("✅ " + size + " integers written to " + fileName + " (space-separated)");
    }
}