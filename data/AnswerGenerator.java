import java.util.*;
import java.io.*;

public class AnswerGenerator {
    public static void main(String[] args) throws IOException {
        String inputFile = "input_1m.txt";
        String outputFile = "answer_1m.txt";

        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        String[] tokens = br.readLine().split(" ");
        br.close();

        int[] nums = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            nums[i] = Integer.parseInt(tokens[i]);
        }

        Arrays.sort(nums);

        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
        for (int i = 0; i < nums.length; i++) {
            bw.write(Integer.toString(nums[i]));
            if (i != nums.length - 1) bw.write(" ");
        }
        bw.newLine();
        bw.close();

        System.out.println("✅ Answer file saved to: " + outputFile);
    }
}
