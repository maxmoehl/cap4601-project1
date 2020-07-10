/**
 * This file contains the main function and executes all of the programs code.
 *
 * @author Maximilian Moehl
 * @date 2020/07/10
 * @info CAP4601
 */
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    private static boolean DEBUG;

    static boolean debugEnabled() {
        return DEBUG;
    }

    // Yanick
    static String[] readDocuments(String folderPath) {
        return null;
    }

    /**
     *
     * @param document
     * @return
     *
     * @author Yanick Schweitzer
     */
    static String[] tokenizeDocument(String document) {
        StringTokenizer st = new StringTokenizer(document);
        return null;
    }

    // Lucas
    static String[] removeStopWords(String[] documents) {
        return null;
    }

    // Lucas
    static HashMap<String, Integer> generateWordFrequencyMap(String[] document) {
        return null;
    }

    // Maximilian
    static void generateInvertedIndex(HashMap<String, Integer>[] frequencyMaps) {

    }

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Missing directory argument");
        }
        // Check if the -debug flag is set and store in DEBUG
        DEBUG = args.length > 1 && args[1].equals("-debug");
        String path = args[0];

        String[] documents = readDocuments(path);
        HashMap<String, Integer>[] frequencyMaps = new HashMap[documents.length];

        for (int i = 0; i < documents.length; i++) {
            String[] tokens = tokenizeDocument(documents[i]);
            tokens = removeStopWords(tokens);
            frequencyMaps[i] = generateWordFrequencyMap(tokens);
        }
        generateInvertedIndex(frequencyMaps);

    }
}
