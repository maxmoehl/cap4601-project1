import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to represent an inverted index. It uses a HashMap as the
 * underlying data structure. This class provides functions that fit the use
 * case of an inverted index and basically just translates them to the calls
 * on the HashMap.
 *
 * @author Maximilian Möhl
 * @date 2020/07/10
 * @info course CAP4601
 */

public class InvertedIndex {

    private HashMap<String, ArrayList<Integer>> store;

    public InvertedIndex() {
        store = new HashMap<>();
    }

    /**
     * Takes multiple frequency maps and builds an InvertedIndex out of them
     * for further usage.
     *
     * @param frequencyMaps should contain frequency maps of multiple documents
     *
     * @author Maximilian Moehl
     */
    public InvertedIndex(HashMap<String, Integer>[] frequencyMaps) {
        super();
        for (int i = 0; i < frequencyMaps.length; i++) {
            // iterate over all contents
            for (Map.Entry<String, Integer> stringIntegerEntry : frequencyMaps[i].entrySet()) {
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
     * Returns the documentIds that are stored for a given word.
     *
     * @param word to be looked up
     * @return a copy of the list that is stored
     */
    public Integer[] getDocumentIds(String word) {
        Integer[] tmp = new Integer[store.get(word).size()];
        store.get(word).toArray(tmp);
        return tmp;
    }
}
