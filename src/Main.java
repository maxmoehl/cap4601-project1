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

    /**
     * Generates a HashMap that uses a word as a key stores the weight for it
     * in the value. For reference, this is the 'translation':<br><br>
     * tfij = frequencyMap.get(word)<br>
     * N = documentCount<br>
     * dfi = ii.getDocumentIds(word).length<br>
     * wij = (1 + log(tfij)) * log(N / dfi)
     *
     * @param ii the inverted index for the documents to be processed
     * @param frequencyMap word frequencies for the document that is being processed
     * @param documentCount total count of documents to be processed
     * @return weighted dictionary
     * @author Maximilian Moehl
     */
    static HashMap<String, Double> generateWeightedDictionary(InvertedIndex ii, HashMap<String, Integer> frequencyMap, int documentCount) {
        HashMap<String, Double> weightedDict = new HashMap<>();
        String[] dict = ii.getDictionary();
        for (String word : dict) {
            weightedDict.put(word, (1 + Math.log(frequencyMap.get(word))) * Math.log((double) documentCount / ii.getDocumentIds(word).length));
        }
        return weightedDict;
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
        if (debugEnabled()) {
            System.out.println(ii.toString());
        }
        // Generate a weighted dictionary for each document and calculate the
        // document-document incidence matrix
    }
}
