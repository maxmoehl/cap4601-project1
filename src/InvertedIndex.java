import java.util.*;

/**
 * This class is used to represent an inverted index. It uses a HashMap as the
 * underlying data structure and provides functions that fit the use
 * case of an inverted index and basically just translates them to calls
 * on the HashMap.
 *
 * @author Maximilian Moehl
 * @date 2020/07/10
 * @info course CAP4601
 */

public class InvertedIndex {

    private final HashMap<String, ArrayList<Integer>> store;

    /**
     * Initializes an empty {@code InvertedIndex}.
     */
    public InvertedIndex() {
        store = new HashMap<>();
    }

    /**
     * Takes a list of frequency maps and builds an InvertedIndex out of them
     * for further usage.
     *
     * @param frequencyMaps should contain frequency maps of multiple documents
     */
    public InvertedIndex(List<HashMap<String, Integer>> frequencyMaps) {
        this();
        for (int i = 0; i < frequencyMaps.size(); i++) {
            // iterate over all contents
            for (Map.Entry<String, Integer> stringIntegerEntry : frequencyMaps.get(i).entrySet()) {
                // and store them in the InvertedIndex
                this.addDocumentId(stringIntegerEntry.getKey(), i);
            }
        }
    }

    /**
     * Adds a new documentId to a given word. If the given word does not exist
     * it will be added to the index.
     *
     * @param word       where the document should be added
     * @param documentId which should be added to the word
     */
    public void addDocumentId(String word, int documentId) {
        if (!store.containsKey(word)) {
            store.put(word, new ArrayList<>());
        }
        store.get(word).add(documentId);
    }

    /**
     * Returns the documentIds that are stored for a given word. If the word is not
     * part of the index, {@code null} will be returned.
     *
     * @param word to be looked up
     * @return a copy of the list that is stored
     */
    public Integer[] getDocumentIds(String word) {
        List<Integer> documentIds = store.get(word);
        if (documentIds == null) {
            return null;
        }
        Integer[] returnArray = new Integer[store.get(word).size()];
        store.get(word).toArray(returnArray);
        return returnArray;
    }

    public String[] getDictionary() {
        String[] dict = new String[store.size()];
        int index = 0;
        for (Map.Entry<String, ArrayList<Integer>> e : store.entrySet()) {
            dict[index] = e.getKey();
        }
        Arrays.sort(dict);
        return dict;
    }

    /**
     * Generates a string representation of the inverted index.<br>
     * Example:<br>
     * banana: 1, 4, 5, 7, 19<br>
     * cherry: 3, 4, 5, 8<br>
     * pea: 4, 3, ...<br>
     *
     * @return constructed string
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Map.Entry<String, ArrayList<Integer>> e : store.entrySet()) {
            s.append(e.getKey());
            s.append(": ");
            for (Integer i : e.getValue()) {
                s.append(i.toString());
                s.append(", ");
            }
            // Remove trailing commas and spaces
            s.delete(s.length() - 2, s.length() - 1);
            s.append("\n");
        }
        return s.toString();
    }
}
