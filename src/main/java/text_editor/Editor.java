package text_editor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Editor {
    public static final double MIN_LENGTH = 100D;
    public static final double MIDDLE_LENGTH = 5_000D;
    public static final double MAX_ITERATIVE_LENGTH = 50000000000000000000D; // StackOverFlow так і не було досягнуто
    public static final double MAX_RECURSION_LENGTH = 5_992D;

    public void navigate() {
        Scanner scanner = new Scanner(System.in);
        double time = 0;
        while (true) {
            choose();
            int choice = scanner.nextInt();
            switch (choice) {
                case 0: {
                    System.exit(0);
                }
                case 1: {
                    time = countTime(new Iterate(), MIN_LENGTH);
                    break;
                }
                case 2: {
                    time = countTime(new Iterate(), MIDDLE_LENGTH);
                    break;
                }
                case 3: {
                    time = countTime(new Iterate(), MAX_ITERATIVE_LENGTH);
                    break;
                }
                case 4: {
                    time = countTime(new Recursion(), MIN_LENGTH);
                    break;
                }
                case 5: {
                    time = countTime(new Recursion(), MIDDLE_LENGTH);
                    break;
                }
                case 6: {
                    time = countTime(new Recursion(), MAX_RECURSION_LENGTH);
                    break;
                } default:
                    System.out.println("You entered the wrong number");
            }
            NumberFormat formatter = new DecimalFormat("#0.00000000000");
            System.out.print("Execution time is " + formatter.format(time / 1000d) + " seconds\n");
        }
    }

    public String readFirstChars(double number) {
        try (Reader r = new BufferedReader(new InputStreamReader(
                new FileInputStream("file.txt"), StandardCharsets.US_ASCII))) {
            StringBuilder resultBuilder = new StringBuilder();
            int count = 0;
            int intch;
            while (((intch = r.read()) != -1) && count < number) {
                resultBuilder.append((char) intch);
                count++;
            }
            return resultBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public String read() {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(new FileInputStream("file.txt")))) {
            String line = null;
            while (true) {
                try {
                    if ((line = br.readLine()) == null) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                resultStringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return resultStringBuilder.toString();
    }

    public void choose() {
        System.out.println("Enter the number of function you'd like to test");
        System.out.println("0 to quit...");
        System.out.println("1. Iterative 100 elements");
        System.out.println("2. Iterative 5 000 elements");
        System.out.println("3. Iterative -> n elements");
        System.out.println("4. Recursion 100 elements");
        System.out.println("5. Recursion 5 000 elements");
        System.out.println("6. Recursion -> n elements");
    }

    public <T extends Algorithm> double countTime(T method, double elements) {
        String sentences = null;
        if (method != null && method.getClass().getSimpleName().equals("Iterate") && elements >= 50_000) {
            sentences = read();
        } else {
            sentences = readFirstChars(elements);
        }
        String exclamatory = findExclamatorySentences(sentences);
        long start = System.currentTimeMillis();
        String results = method != null ? method.reverseSentence(exclamatory) : null;
        long end = System.currentTimeMillis();
        System.out.println();
        System.out.println("\n"
                + (method != null ? method.getClass().getSimpleName() : null)
                + " " + elements +  " elements");
        writeIntoFile(results);
        return end - start;
    }

    public String findExclamatorySentences(String text) {
        List<String> collect = Arrays.stream(text.split("(?<=[?.!])"))
                .map(sentence -> sentence.replaceAll("(?=[A-Z])", " ").trim() )
                .collect(Collectors.toList());
        StringBuilder builder = new StringBuilder();
        for (String str : collect) {
            if (str.endsWith("!")) {
                builder.append(str).append(" ");
            }
        }
        return builder.toString();
    }

    public void writeIntoFile(String sentences) {
        try {
            FileWriter myWriter = new FileWriter("output.txt");
            myWriter.write(sentences);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
