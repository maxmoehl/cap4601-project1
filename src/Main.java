import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

/**
 * This file contains the main function and executes all of the programs code.
 *
 * @author Yanick Schwitzer, Lucas Timm, Maximilian Moehl
 * @date 2020/07/10
 * @info course CAP4601
 */

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

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Missing directory argument");
        }
        // Check if the -debug flag is set and store in DEBUG
        DEBUG = args.length > 1 && args[1].equals("-debug");
        String path = args[0];

        String[] documents = readDocuments(path);
        List<HashMap<String, Integer>> frequencyMaps = new ArrayList<>();

        for (int i = 0; i < documents.length; i++) {
            String[] tokens = tokenizeDocument(documents[i]);
            tokens = removeStopWords(tokens);
            frequencyMaps.set(i, generateWordFrequencyMap(tokens));
        }
        InvertedIndex ii = new InvertedIndex(frequencyMaps);
    }
}
