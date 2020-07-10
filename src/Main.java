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
     * @param document
     * @return
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
     * @param ii            the inverted index for the documents to be processed
     * @param frequencyMap  word frequencies for the document that is being processed
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

    /**
     * Takes two dictionaries that map words to their weights for one document and
     * calculates the cosine similarity from them.
     *
     * @param weightedDictionary1 first document
     * @param weightedDictionary2 second document
     * @return cosine similarity
     * @author Maximilian Moehl
     */
    static double cosineSimilarity(HashMap<String, Double> weightedDictionary1, HashMap<String, Double> weightedDictionary2) {
        // extract values as an array for easier calculations
        Double[] weightsDict1 = weightedDictionary1.values().toArray(new Double[0]);
        Double[] weightsDict2 = weightedDictionary2.values().toArray(new Double[0]);
        return scalarProduct(weightsDict1, weightsDict2) / (Math.sqrt(sumOfSquares(weightsDict1)) * Math.sqrt(sumOfSquares(weightsDict2)));
    }

    /**
     * Calculates the scalar product (also called inner product) of two vectors
     * that have the same size
     * @param arr1 first array of doubles
     * @param arr2 second array of doubles
     * @return scalar product
     * @throws ArithmeticException when size of arrays does not match
     * @author Maximilian Moehl
     */
    static double scalarProduct(Double[] arr1, Double[] arr2) {
        double sum = 0;
        if (arr1.length != arr2.length) {
            throw new ArithmeticException("Can not calculate scalar product if arrays don't have same size");
        }
        for (int i = 0; i < arr1.length; i++) {
            sum += arr1[i] * arr2[i];
        }
        return sum;
    }

    /**
     * Takes a given array of doubles, raises every value to the power
     * of two and sums them up.
     * @param array to be processed
     * @return sum of squares
     * @author Maximilian Moehl
     */
    static double sumOfSquares(Double[] array) {
        double sum = 0;
        for (double d : array) {
            sum += Math.pow(d, 2);
        }
        return sum;
    }

    /**
     * Takes the finished document matrix, builds a string
     * representing the whole matrix including labels and
     * prints it to the console
     *
     * @param m document matrix containing cosine similarity values
     * @author Maximilian Moehl
     */
    static void printDocumentMatrixToConsole(double[][] m) {
        StringBuilder sb = new StringBuilder("\t");
        // Add the title line containing the labels for each column
        for (int i = 0; i < m.length; i++) {
            sb.append("d");
            sb.append(i + 1);
            sb.append("\t");
        }
        // Print each line of the matrix, preceded by one label for the row
        for (int i = 0; i < m.length; i++) {
            sb.append('d');
            sb.append(i + 1);
            sb.append("\t");
            for (int j = 0; j < m[i].length; j++) {
                sb.append(m[i][j]);
                sb.append('\t');
            }
            // Remove trailing tab
            sb.deleteCharAt(sb.length() - 1);
        }
        System.out.println(sb.toString());
    }

    /**
     * The main function of this java program
     *
     * @param args command line arguments
     * @author Maximilian Moehl
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Missing directory argument");
        }
        // Check if the -debug flag is set and store in DEBUG
        DEBUG = args.length > 1 && args[1].equals("-debug");
        String path = args[0];

        // Read all documents from the given folder
        String[] documents = readDocuments(path);

        // Generate a frequency map for each document containing the frequency of each word
        List<HashMap<String, Integer>> frequencyMaps = new ArrayList<>();
        for (int i = 0; i < documents.length; i++) {
            String[] tokens = tokenizeDocument(documents[i]);
            tokens = removeStopWords(tokens);
            frequencyMaps.set(i, generateWordFrequencyMap(tokens));
        }

        // Generate the inverted index from all frequency maps
        InvertedIndex ii = new InvertedIndex(frequencyMaps);
        if (debugEnabled()) {
            System.out.println(ii.toString());
        }

        // Generate the weighted dictionaries for each document
        List<HashMap<String, Double>> weightedDictionaries = new ArrayList<>();
        for (int i = 0; i < frequencyMaps.size(); i++) {
            weightedDictionaries.set(i, generateWeightedDictionary(ii, frequencyMaps.get(i), documents.length));
        }

        // Combine the weights into one big matrix using the cosine similarity
        double[][] similarityMatrix = new double[documents.length][documents.length];
        for (int i = 0; i < documents.length; i++) {
            for (int j = 0; j < documents.length; j++) {
                similarityMatrix[i][j] = cosineSimilarity(weightedDictionaries.get(i), weightedDictionaries.get(j));
            }
        }

        // Print to results to the console
        printDocumentMatrixToConsole(similarityMatrix);
    }
}
