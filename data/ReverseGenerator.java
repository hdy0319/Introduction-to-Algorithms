import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ReverseGenerator {
    public static void main(String[] args) throws Exception {
        String inputPath = "answer_1m.txt";
        String outputPath = "reverse_1m.txt";

        String[] tokens = Files.readString(Path.of(inputPath)).trim().split(" ");

        StringBuilder reversed = new StringBuilder();
        for (int i = tokens.length - 1; i >= 0; i--) {
            reversed.append(tokens[i]);
            if (i > 0) reversed.append(" ");
        }

        Files.writeString(Path.of(outputPath), reversed.toString());

        System.out.println("Reversed sequence saved to " + outputPath);
    }
}