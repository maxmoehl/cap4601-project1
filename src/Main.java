import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

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

    /**
     * @param filePath
     * @return
     * @author Yanick Schweitzer
     */
    static String[] getStopWords(String filePath) {
        String stopWordString;
        try {
            stopWordString = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException("Could not read stopwords file.");
        }

        return tokenizeDocument(stopWordString);
    }

    /**
     * @param folderPath
     * @return
     * @author Yanick Schweitzer
     */
    static String[] readDocuments(String folderPath) {
        File folderDir = new File(folderPath);
        ArrayList<String> filenames = new ArrayList<>();

        File[] fileList = folderDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        });

        // check if any files were found
        if (fileList == null) {
            throw new RuntimeException("No documents found.");
        }

        for (File file : fileList) {
            filenames.add(folderPath + File.separator + file.getName());
        }

        String[] documentsArray = new String[filenames.size()];

        for (int i = 0; i < filenames.size(); i++) {
            String currentDocumentString;
            try {
                currentDocumentString = new String(Files.readAllBytes(Paths.get(filenames.get(i))));
            } catch (IOException e) {
                throw new RuntimeException("Could not read document file.");
            }
            documentsArray[i] = currentDocumentString;
        }

        return documentsArray;
    }

    /**
     * @param document
     * @return
     * @author Yanick Schweitzer
     */
    static String[] tokenizeDocument(String document) {
        StringTokenizer st = new StringTokenizer(document);
        ArrayList<String> tokens = new ArrayList<>();
        while (st.hasMoreTokens()) {
            tokens.add(st.nextToken());
        }
        String[] returnArray = new String[tokens.size()];

        return tokens.toArray(returnArray);
    }

    /**
     * @author Lucas Timm
     */
    static String[] removeStopWords(String[] document, String[] stopWords) {
        ArrayList<String> cleanedWordList = new ArrayList<>();
        for (String word : document) {
            if (Arrays.binarySearch(stopWords, word) < 0) {
                cleanedWordList.add(word);
            }
        }
        String[] cleanedStringArray = new String[cleanedWordList.size()];
        return cleanedWordList.toArray(cleanedStringArray);
    }

    /**
     * @param document Document that should be analyzed by word count
     * @return Word frequency table as a HashMap data type
     * @author Lucas Timm
     */
    static HashMap<String, Integer> generateWordFrequencyMap(String[] document) {
        HashMap<String, Integer> wordFrequencyMap = new HashMap<>();
        for (String word : document) {
            if (wordFrequencyMap.containsKey(word)) {
                int val = wordFrequencyMap.get(word);
                wordFrequencyMap.put(word, val + 1);
            } else {
                wordFrequencyMap.put(word, 1);
            }
        }
        return wordFrequencyMap;
    }

    /**
     * Generates a HashMap that uses words as keys and stores the weight for them
     * in the value. If the word is not present in the document the weight gets set
     * to zero. For reference, this is the 'translation':<br><br>
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
            if (frequencyMap.get(word) == null) {
                weightedDict.put(word, 0.0);
            } else {
                weightedDict.put(word, (1 + Math.log(frequencyMap.get(word))) * Math.log((double) documentCount / ii.getDocumentIds(word).length));
            }
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
     * @throws ArithmeticException if any dictionary has a different length than the others
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
     *
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
     *
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
     * prints it to the console.
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
        sb.append('\n');
        // Print each line of the matrix, preceded by one label for the row
        for (int i = 0; i < m.length; i++) {
            sb.append('d');
            sb.append(i + 1);
            sb.append('\t');
            for (int j = 0; j < m[i].length; j++) {
                sb.append(m[i][j]);
                sb.append('\t');
            }
            // Remove trailing tab
            sb.deleteCharAt(sb.length() - 1);
            sb.append('\n');
        }
        System.out.println(sb.toString());
    }

    /**
     * The main function of this java program. Each document gets assigned an
     * id starting with 0 for the first document that is read and counting upwards
     * for every additional document.
     *
     * @param args command line arguments
     * @author Lucas Timm, Maximilian Moehl, Yanick Schweitzer
     */
    public static void main(String[] args) {
        if (args.length < 2 || args.length > 3) {
            throw new IllegalArgumentException("Usage: Main <documents folder path> <stop-words file> [-debug]");
        }
        // Check if the -debug flag is set and store in DEBUG
        DEBUG = args.length == 3 && args[2].equals("-debug");

        String documentFolderPath = args[0];
        String stopWordsFilePath = args[1];

        // Read stop words and documents
        String[] stopWords = getStopWords(stopWordsFilePath);
        String[] documents = readDocuments(documentFolderPath);

        // Generate a frequency map for each document containing the frequency of each word
        List<HashMap<String, Integer>> frequencyMaps = new ArrayList<>();
        for (String document : documents) {
            String[] tokens = tokenizeDocument(document);
            tokens = removeStopWords(tokens, stopWords);
            frequencyMaps.add(generateWordFrequencyMap(tokens));
        }

        // Generate the inverted index from all frequency maps
        InvertedIndex ii = new InvertedIndex(frequencyMaps);
        if (debugEnabled()) {
            System.out.println(ii.toString());
        }

        // Generate the weighted dictionaries for each document
        List<HashMap<String, Double>> weightedDictionaries = new ArrayList<>();
        for (HashMap<String, Integer> frequencyMap : frequencyMaps) {
            weightedDictionaries.add(generateWeightedDictionary(ii, frequencyMap, documents.length));
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
