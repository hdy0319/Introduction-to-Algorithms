import java.util.*;
import java.io.*;

public class SequenceGenerator {
    public static void main(String[] args) throws IOException {
        int size = 1000000;
        int min = 0;
        int max = 99_999;
        String fileName = "input_1m.txt";

        Random rand = new Random();
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            data.add(rand.nextInt(max - min + 1) + min); // 중복 허용 랜덤 값
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        for (int i = 0; i < size; i++) {
            bw.write(data.get(i).toString());
            if (i != size - 1) bw.write(" ");
        }
        bw.newLine();
        bw.close();

        System.out.println("✅ " + size + " integers (with possible duplicates) written to " + fileName);
    }
}